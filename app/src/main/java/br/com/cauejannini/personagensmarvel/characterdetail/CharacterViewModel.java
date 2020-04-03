package br.com.cauejannini.personagensmarvel.characterdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.cauejannini.personagensmarvel.commons.MyLog;
import br.com.cauejannini.personagensmarvel.commons.integration.MarvelRepository;
import br.com.cauejannini.personagensmarvel.commons.models.Character;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterViewModel extends ViewModel {

    public MutableLiveData<Bitmap> image = new MutableLiveData<>();
    private ObservableField<String> id = new ObservableField<>();
    private ObservableField<String> name = new ObservableField<>();
    private ObservableField<String> description = new ObservableField<>();
    private ObservableField<String> resourceURI = new ObservableField<>();

    private String thumbnailUrl;

    public void init(Character character) {
        this.id.set(String.valueOf(character.getId()));
        this.name.set(character.getName());
        this.description.set(character.getDescription());
        this.resourceURI.set(character.getResourceURI());

        this.thumbnailUrl = character.getThumbnail().getPath() + "/landscape_xlarge." + character.getThumbnail().getExtension();
        loadImage();
    }

    public ObservableField<String> getId() {
        return id;
    }

    public void setId(ObservableField<String> id) {
        this.id = id;
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(ObservableField<String> name) {
        this.name = name;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public void setDescription(ObservableField<String> description) {
        this.description = description;
    }

    public ObservableField<String> getResourceURI() {
        return resourceURI;
    }

    public void setResourceURI(ObservableField<String> resourceURI) {
        this.resourceURI = resourceURI;
    }

    public LiveData<Bitmap> getImage() {
        return image;
    }

    private void loadImage() {
        if (thumbnailUrl != null) {
            MarvelRepository.get().fetchImage(thumbnailUrl).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            image.setValue(BitmapFactory.decodeStream(response.body().byteStream()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    MyLog.e("fetch image error", t.getMessage());
                }
            });
        }
    }

}
