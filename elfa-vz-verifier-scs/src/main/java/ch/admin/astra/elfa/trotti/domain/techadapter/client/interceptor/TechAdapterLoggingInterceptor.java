package ch.admin.astra.elfa.trotti.domain.techadapter.client.interceptor;

import lombok.experimental.UtilityClass;
import okhttp3.logging.HttpLoggingInterceptor;

import static ch.admin.astra.elfa.trotti.domain.techadapter.client.interceptor.TechAdapterHeaderInterceptor.X_API_KEY;

@UtilityClass
public class TechAdapterLoggingInterceptor {

    public HttpLoggingInterceptor getInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpLoggingInterceptor.redactHeader(X_API_KEY);
        return httpLoggingInterceptor;
    }
}
