package br.com.cauejannini.personagensmarvel.integration;

public class MarvelRepository {

    private static MarvelApi marvelApi;

    public static MarvelApi get() {
        if (marvelApi == null) {
            marvelApi = RetrofitService.createService(MarvelApi.class);
        }
        return marvelApi;
    }



}
