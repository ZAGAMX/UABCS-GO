package sifuentes.uabcsgo.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sifuentes.uabcsgo.ActualizarNotaActivity;
import sifuentes.uabcsgo.AgregarNotasActivity;
import sifuentes.uabcsgo.Models.Notas;
import sifuentes.uabcsgo.R;

public class AdapterNotes extends RecyclerView.Adapter<AdapterNotes.ViewHolderNotes>  {


    List<Notas> listNotes;

    public AdapterNotes(List<Notas> listMovie) {
        this.listNotes = listMovie;
    }

    public ViewHolderNotes onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_all_notes, null, false);
        return new ViewHolderNotes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderNotes holder, int position) {
        Notas notas = listNotes.get(position);
        holder.notas = notas;
        final Gson GSON = new Gson();
        holder.bind();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ActualizarNotaActivity.class);
                i.putExtra("JSON", GSON.toJson(holder.notas));
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public class ViewHolderNotes extends RecyclerView.ViewHolder {
        public Notas notas;
        TextView title;
        public ViewHolderNotes(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.idNotasTitulo);
        }

        public void bind() {
            title.setText(notas.getTitle());
        }
    }
}
