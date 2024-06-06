package ch.admin.astra.elfa.trotti.domain.techadapter.client.interceptor;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * The TechAdapterHeaderInterceptor class is responsible for adding the necessary headers
 * to the outgoing requests before they are sent to the TechAdapter.
 */
@Component
@RequiredArgsConstructor
public class TechAdapterHeaderInterceptor implements Interceptor {

    public static final String X_API_KEY = "x-api-key";
    public static final String ACCEPT = "accept";
    public static final String CONTENT_TYPE = "Content-Type";

    @Value("${techAdapter.password}")
    private String password;

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = buildRequest(chain);

        return chain.proceed(request);
    }

    private Request buildRequest(Chain chain) {
        return chain.request()
                .newBuilder()
                .header(X_API_KEY, password)
                .header(ACCEPT, MediaType.APPLICATION_JSON.toString())
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .build();
    }
}