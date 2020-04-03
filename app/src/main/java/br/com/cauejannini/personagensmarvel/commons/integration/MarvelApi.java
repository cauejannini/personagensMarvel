package br.com.cauejannini.personagensmarvel.commons.integration;

import br.com.cauejannini.personagensmarvel.commons.models.CharacterDataWrapper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface MarvelApi {

    @GET("public/characters")
    Call<CharacterDataWrapper> getCharacters(@Query("offset") int offset);

    @GET("public/characters/{characterId}")
    Call<CharacterDataWrapper> getCharacter(@Path("characterId") String characterId);

    @GET
    Call<ResponseBody> fetchImage(@Url String url);
}
