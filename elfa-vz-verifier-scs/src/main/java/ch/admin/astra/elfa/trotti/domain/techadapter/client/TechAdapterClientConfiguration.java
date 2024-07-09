package ch.admin.astra.elfa.trotti.domain.techadapter.client;

import ch.admin.astra.elfa.trotti.domain.techadapter.client.impl.MockTechAdapterClientImpl;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.impl.TechAdapterClientImpl;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.interceptor.TechAdapterHeaderInterceptor;
import ch.admin.astra.elfa.trotti.domain.techadapter.client.interceptor.TechAdapterLoggingInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class TechAdapterClientConfiguration {

    private final ObjectMapper objectMapper;

    private final TechAdapterHeaderInterceptor techAdapterHeaderInterceptor;

    @Profile("!local")
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public TechAdapterClient getTechAdapterClient(@Value("${techAdapter.endpoint}") String endpoint) {

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addNetworkInterceptor(techAdapterHeaderInterceptor)
                        .addInterceptor(TechAdapterLoggingInterceptor.getInterceptor())
                        .build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .baseUrl(endpoint)
                .build();

        TechAdapterApi api = retrofit.create(TechAdapterApi.class);
        return new TechAdapterClientImpl(api, objectMapper);
    }

    @Profile("local")
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_APPLICATION)
    public TechAdapterClient getMockTechAdapterClient() {
        return new MockTechAdapterClientImpl();
    }
}