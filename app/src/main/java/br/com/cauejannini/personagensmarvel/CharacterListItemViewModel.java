package br.com.cauejannini.personagensmarvel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.com.cauejannini.personagensmarvel.integration.MarvelRepository;
import br.com.cauejannini.personagensmarvel.models.Character;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterListItemViewModel {

    private Character character;
    private String name;
    private MutableLiveData<Bitmap> thumbnail = new MutableLiveData<>();

    public CharacterListItemViewModel(Character character) {
        init(character);
    }

    public void init(Character character) {
        this.character = character;
        this.name = character.getName();
        loadImage();
    }

    private void loadImage() {

        String url = character.getThumbnail().getPath()+"/standard_medium."+character.getThumbnail().getExtension();

        MarvelRepository.get().fetchImage(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        thumbnail.setValue(BitmapFactory.decodeStream(response.body().byteStream()));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MyLog.e("image fetch error", t.getMessage());
            }
        });
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public LiveData<Bitmap> getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(MutableLiveData<Bitmap> thumbnail) {
        this.thumbnail = thumbnail;
    }
}
