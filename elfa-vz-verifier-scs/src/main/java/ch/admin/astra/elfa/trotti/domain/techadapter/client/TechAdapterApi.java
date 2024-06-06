package ch.admin.astra.elfa.trotti.domain.techadapter.client;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationRequest;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.model.VerificationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.UUID;

/**
 * TechAdapterApi is an interface that defines the endpoints for connecting to the Verifier Tech-Adapter.
 */
public interface TechAdapterApi {

    /**
     * Makes an API call to start the verification process.
     *
     * @param verificationRequest The verification request object containing the required attributes and client metadata.
     * @return The verification response object if the API call is successful.
     */
    @POST("/verification/")
    Call<VerificationResponse> startVerification(@Body VerificationRequest verificationRequest);

    /**
     * Retrieves the verification details for a given verification ID.
     *
     * @param verificationId The ID of the verification to retrieve.
     * @return A Call object representing the asynchronous operation to retrieve the verification details.
     */
    @GET("/verification/{verificationId}")
    Call<VerificationResponse> getVerification(@Path("verificationId") UUID verificationId);

}
