package ch.admin.astra.elfa.trotti.domain.techadapter.client.impl;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.TechAdapterApi;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.exception.TechAdapterException;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.ErrorDescription;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationRequest;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class TechAdapterClientImplTest {

    @InjectMocks
    private TechAdapterClientImpl techAdapterClient;

    @Mock
    private TechAdapterApi techAdapterApi;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Call<VerificationResponse> call;

    @Mock
    private Response<VerificationResponse> responseMock;

    @Test
    void startVerification_success() throws IOException {
        VerificationRequest verificationRequest = VerificationRequest.builder().build();
        Mockito.when(techAdapterApi.startVerification(verificationRequest)).thenReturn(call);

        VerificationResponse expected = VerificationResponse.builder().build();
        Mockito.when(call.execute()).thenReturn(Response.success(expected));

        VerificationResponse result = techAdapterClient.startVerification(verificationRequest);

        Assertions.assertEquals(expected, result);
    }

    @Test
    void startVerification_InternalServerError_throws() throws IOException {
        VerificationRequest verificationRequest = VerificationRequest.builder().build();
        Mockito.when(techAdapterApi.startVerification(verificationRequest)).thenReturn(call);

        Mockito.when(responseMock.isSuccessful()).thenReturn(false);
        Mockito.when(responseMock.code()).thenReturn(500);
        Mockito.when(responseMock.message()).thenReturn("Internal Server Error");
        Mockito.when(call.execute()).thenReturn(responseMock);

        TechAdapterException exception = Assertions.assertThrows(TechAdapterException.class, () -> techAdapterClient.startVerification(verificationRequest));

        Assertions.assertEquals("Internal Server Error", exception.getDetails().get(0));
    }

    @Test
    void startVerification_Unauthorized_throws() throws IOException {
        VerificationRequest verificationRequest = VerificationRequest.builder().build();
        Mockito.when(techAdapterApi.startVerification(verificationRequest)).thenReturn(call);

        Mockito.when(responseMock.isSuccessful()).thenReturn(false);
        Mockito.when(responseMock.code()).thenReturn(401);
        Mockito.when(responseMock.message()).thenReturn("Unauthorized");

        String detailText = "Invalid or missing API Key";
        String errorBodyText = """
                {
                  "detail": "Invalid or missing API Key"
                }
                """;
        ErrorDescription errorDescription = ErrorDescription.builder().detail(detailText).build();
        ResponseBody responseBody = ResponseBody.create(errorBodyText, MediaType.get("application/json"));
        Mockito.when(responseMock.errorBody()).thenReturn(responseBody);
        Mockito.when(objectMapper.readValue(errorBodyText, ErrorDescription.class)).thenReturn(errorDescription);

        Mockito.when(call.execute()).thenReturn(responseMock);

        TechAdapterException exception = Assertions.assertThrows(TechAdapterException.class, () -> techAdapterClient.startVerification(verificationRequest));

        Assertions.assertEquals("Unauthorized", exception.getDetails().get(0));
        Assertions.assertEquals("Invalid or missing API Key", exception.getDetails().get(1));
    }

    @Test
    void startVerification_exception() throws IOException {
        VerificationRequest verificationRequest = VerificationRequest.builder().build();
        Mockito.when(techAdapterApi.startVerification(verificationRequest)).thenReturn(call);
        Mockito.when(call.execute()).thenThrow(IOException.class);

        Assertions.assertThrows(TechAdapterException.class, () -> techAdapterClient.startVerification(verificationRequest));
    }
}