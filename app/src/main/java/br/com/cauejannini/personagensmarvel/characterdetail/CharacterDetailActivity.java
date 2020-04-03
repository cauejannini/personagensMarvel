package br.com.cauejannini.personagensmarvel.characterdetail;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import br.com.cauejannini.personagensmarvel.R;
import br.com.cauejannini.personagensmarvel.databinding.ActivityCharacterDetailBinding;
import br.com.cauejannini.personagensmarvel.commons.models.Character;

public class CharacterDetailActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_CHARACTER = "character";

    CharacterViewModel characterVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);

        // Get Character from intent
        Character character = null;
        try {
            character = (Character) getIntent().getSerializableExtra(EXTRA_KEY_CHARACTER);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) { e.printStackTrace(); }

        if (character == null) {
            finish();
            return;
        }

        // Init VM
        characterVM = new ViewModelProvider(this).get(CharacterViewModel.class);
        characterVM.init(character);

        // Bindings
        ActivityCharacterDetailBinding activityCharacterDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail);
        activityCharacterDetailBinding.setCharacterVM(characterVM);

        ImageView imageView = findViewById(R.id.imageView);

        // LiveData
        characterVM.getImage().observe(this, imageView::setImageBitmap);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
