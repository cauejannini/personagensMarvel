package br.com.cauejannini.personagensmarvel.characterlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import br.com.cauejannini.personagensmarvel.R;
import br.com.cauejannini.personagensmarvel.characterdetail.CharacterDetailActivity;
import br.com.cauejannini.personagensmarvel.commons.models.Character;
import br.com.cauejannini.personagensmarvel.databinding.ActivityCharacterListBinding;

public class CharacterListActivity extends AppCompatActivity implements CharactersAdapter.InteractionListener {

    SwipeRefreshLayout srl;
    ProgressBar progressBar;
    RecyclerView rvCharacters;

    TextView tvErrorMessage;

    CharactersAdapter adapter = new CharactersAdapter(this);

    CharacterListViewModel characterListVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init VM
        characterListVM = new ViewModelProvider(this).get(CharacterListViewModel.class);
        characterListVM.init();

        ActivityCharacterListBinding activityCharacterListBinding = DataBindingUtil.setContentView(this, R.layout.activity_character_list);
        activityCharacterListBinding.setCharacterListVM(characterListVM);

        // Binding
        progressBar = findViewById(R.id.progressBar);
        srl = findViewById(R.id.srl);
        rvCharacters = findViewById(R.id.rvCharacters);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);

        // Comportamento
        rvCharacters.setLayoutManager(new LinearLayoutManager(this));
        rvCharacters.setAdapter(adapter);
        rvCharacters.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager instanceof LinearLayoutManager) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();

                    boolean bottomHasBeenReached = lastVisible + 1 == totalItemCount;
                    if (!srl.isRefreshing() && bottomHasBeenReached && totalItemCount > 0) {
                        characterListVM.loadMore();
                    }
                }
            }
        });

        srl.setOnRefreshListener(() -> characterListVM.loadCharactersFromStart());

        characterListVM.getIsLoading().observe(this, isLoading -> {
            srl.setRefreshing(isLoading);
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        characterListVM.getCharacterList().observe(this, characterList -> {
            rvCharacters.setVisibility(View.VISIBLE);
            adapter.setCharactersArray(characterList);
        });

        characterListVM.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage == null || errorMessage.trim().isEmpty()) {
                tvErrorMessage.setVisibility(View.GONE);
                rvCharacters.setVisibility(View.VISIBLE);
            } else {
                rvCharacters.setVisibility(View.GONE);
                tvErrorMessage.setVisibility(View.VISIBLE);
                tvErrorMessage.setText(errorMessage);
            }
        });

    }

    @Override
    public void onCharacterClicked(Character character) {
        Intent intent = new Intent(this, CharacterDetailActivity.class);
        intent.putExtra(CharacterDetailActivity.EXTRA_KEY_CHARACTER, character);
        startActivity(intent);
    }
}
