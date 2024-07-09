package ch.admin.astra.elfa.trotti.domain.techadapter.client.impl;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.TechAdapterApi;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.TechAdapterClient;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.exception.TechAdapterException;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RequiredArgsConstructor
@Slf4j
public class TechAdapterClientImpl implements TechAdapterClient {

    private final TechAdapterApi techAdapterApi;
    private final ObjectMapper objectMapper;

    @Override
    public VerificationResponse startVerification(VerificationRequest verificationRequest) {
        return callApi(techAdapterApi.startVerification(verificationRequest));
    }

    @Override
    public VerificationResponse getVerificationStatus(UUID verificationId) {
        return callApi(techAdapterApi.getVerification(verificationId));
    }

    /**
     * Makes a synchronous API call using the given TechAdapterApi object and returns the response.
     * If the response is successful, the body of the response is returned.
     * If the response is not successful, a TechAdapterException is thrown.
     *
     * @param techAdapterApi The TechAdapterApi object used to make the API call.
     * @return The body of the response if it is successful, null otherwise.
     * @throws TechAdapterException If there is an error making the API call or the response is not successful.
     */
    @Nullable
    private VerificationResponse callApi(Call<VerificationResponse> techAdapterApi) {
        try {
            Response<VerificationResponse> response = techAdapterApi.execute();
            if (response.isSuccessful()) {
                return response.body();
            }

            throw new TechAdapterException(null, mapErrorToException(response));
        } catch (IOException e) {
            throw new TechAdapterException(e, null);
        }
    }

    /**
     * Maps the error response from the Tech Adapter API to a list of exception messages.
     * @param response The response from the Tech Adapter API.
     * @return A list of exception messages.
     * @throws IOException If there is an error reading the error body.
     */
    private List<String> mapErrorToException(Response<VerificationResponse> response) throws IOException {
        // map validation message
        if (UNPROCESSABLE_ENTITY.value() == response.code()) {
            try (ResponseBody body = response.errorBody()) {
                assert body != null;
                ValidationError error = objectMapper.readValue(body.string(), ValidationError.class);
                return Arrays.stream(error.getValidationErrorDetails()).map(ValidationErrorDetail::getMsg).toList();
            }
        } else if (INTERNAL_SERVER_ERROR.value() == response.code()) {
            return List.of(response.message());
        } else {
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add(response.message());

            try (ResponseBody errorBody = response.errorBody()) {
                assert errorBody != null;
                // errorBody can only be consumed once
                String errorBodyString = errorBody.string();
                errorMessages.add(objectMapper.readValue(errorBodyString, ErrorDescription.class).getDetail());
            }

            return errorMessages;
        }
    }

}
