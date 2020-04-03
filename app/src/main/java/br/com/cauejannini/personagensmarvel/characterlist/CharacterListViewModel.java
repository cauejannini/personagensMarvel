package br.com.cauejannini.personagensmarvel.characterlist;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import br.com.cauejannini.personagensmarvel.commons.MyLog;
import br.com.cauejannini.personagensmarvel.commons.integration.MarvelRepository;
import br.com.cauejannini.personagensmarvel.commons.models.Character;
import br.com.cauejannini.personagensmarvel.commons.models.CharacterDataContainer;
import br.com.cauejannini.personagensmarvel.commons.models.CharacterDataWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CharacterListViewModel extends ViewModel {

    private MutableLiveData<List<CharacterListItemViewModel>> characterList = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private static final String ERROR_MESSAGE_EMPTY = "Nenhum personagem a listar!";
    private static final String ERROR_MESSAGE_ERROR = "Ocorreu algum erro. Toque para tentar novamente.";

    public void init() {
        this.characterList.setValue(new ArrayList<>());
        loadCharactersFromStart();
    }

    public void loadMore() {
        if (listHasEnded) return;

        loadCharacters(nextOffset);
    }

    public LiveData<List<CharacterListItemViewModel>> getCharacterList() {
        return characterList;
    }

    private int nextOffset = 0;
    private boolean listHasEnded = false;

    public void loadCharactersFromStart() {
        nextOffset = 0;
        listHasEnded = false;
        loadCharacters(nextOffset);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(MutableLiveData<Boolean> isLoading) {
        this.isLoading = isLoading;
    }

    public MutableLiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private void loadCharacters(int offsetResults) {

        isLoading.setValue(true);
        MarvelRepository.get().getCharacters(offsetResults).enqueue(new Callback<CharacterDataWrapper>() {

            @Override
            public void onResponse(Call<CharacterDataWrapper> call, Response<CharacterDataWrapper> response) {
                isLoading.setValue(false);

                if (response.isSuccessful()) {
                    try {

                        CharacterDataContainer data = response.body().getData();

                        int offset = data.getOffset();
                        int totalReturned = data.getCount();
                        int totalCount = data.getTotal();

                        nextOffset = offset + totalReturned;

                        listHasEnded = nextOffset >= totalCount;

                        if (offset == 0) characterList.setValue(new ArrayList<>());

                        if (totalCount == 0) {
                            errorMessage.setValue(ERROR_MESSAGE_EMPTY);
                        } else if (totalReturned > 0) {
                            Character[] characters = data.getResults();
                            if (characters != null && characters.length > 0) {
                                List<CharacterListItemViewModel> old = characterList.getValue();
                                for (Character character: characters) {
                                    CharacterListItemViewModel clivm = new CharacterListItemViewModel(character);
                                    old.add(clivm);
                                }
                                characterList.setValue(old);
                                errorMessage.setValue("");
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        errorMessage.setValue(ERROR_MESSAGE_ERROR);
                    }
                } else {
                    errorMessage.setValue(ERROR_MESSAGE_ERROR);
                }
            }

            @Override
            public void onFailure(Call<CharacterDataWrapper> call, Throwable t) {
                isLoading.setValue(false);
                MyLog.e("get character error", t.getMessage());
                errorMessage.setValue(ERROR_MESSAGE_ERROR);
            }
        });
    }

}
