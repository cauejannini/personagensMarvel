package br.com.cauejannini.personagensmarvel.characterlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import br.com.cauejannini.personagensmarvel.R;
import br.com.cauejannini.personagensmarvel.commons.models.Character;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {

    private AppCompatActivity activity;
    private List<CharacterListItemViewModel> characterItemVMs = new ArrayList<>();
    private InteractionListener mListener;

    public CharactersAdapter(AppCompatActivity activity) {
        this.activity = activity;
        if (activity instanceof InteractionListener) {
            this.mListener = (InteractionListener) activity;
        }
    }

    public void setCharactersArray(List<CharacterListItemViewModel> characterItemVMs){
        if (characterItemVMs != null) {
            this.characterItemVMs = characterItemVMs;
        } else {
            this.characterItemVMs = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_personagem, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.ivPersonagem.setImageBitmap(null);
        CharacterListItemViewModel characterItemVM = characterItemVMs.get(position);
        if (characterItemVM != null) {
            holder.tvNomePersonagem.setText(characterItemVM.getName());

            characterItemVM.getThumbnail().observe(activity, bitmap -> holder.ivPersonagem.setImageBitmap(bitmap));

            holder.itemView.setOnClickListener(view -> {
                if (mListener != null) mListener.onCharacterClicked(characterItemVM.getCharacter());
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.characterItemVMs.size();
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomePersonagem;
        ImageView ivPersonagem;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomePersonagem = itemView.findViewById(R.id.tvNomePersonagem);
            ivPersonagem = itemView.findViewById(R.id.ivPersonagem);
        }
    }

    public interface InteractionListener {
        void onCharacterClicked(Character character);
    }
}
