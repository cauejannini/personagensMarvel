package br.com.cauejannini.personagensmarvel.integration;

import br.com.cauejannini.personagensmarvel.BuildConfig;
import br.com.cauejannini.personagensmarvel.Utils;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitService {

    private static final String baseUrl = "https://gateway.marvel.com/v1/";

    public static <T> T createService(Class<T> serviceClass) {
        return retrofit.create(serviceClass);
    }

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(buildClient())
            .build();

    private static OkHttpClient buildClient() {

        Interceptor authInterceptor = chain -> {

            String timestamp = Utils.currentTimestamp();
            String apikey = BuildConfig.MarvelPublicKey;
            String toHash = timestamp + BuildConfig.MarvelPrivateKey + BuildConfig.MarvelPublicKey;
            String hash = Utils.md5Hash(toHash);

            Request request = chain.request();
            HttpUrl url = chain.request().url().newBuilder()
                    .addQueryParameter("ts", timestamp)
                    .addQueryParameter("apikey", apikey)
                    .addQueryParameter("hash", hash)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        };

        return new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build();
    }

}
