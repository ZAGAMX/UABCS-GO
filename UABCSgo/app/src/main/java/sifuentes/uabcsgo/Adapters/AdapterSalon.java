package sifuentes.uabcsgo.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import sifuentes.uabcsgo.MainActivity;
import sifuentes.uabcsgo.Models.Salones;
import sifuentes.uabcsgo.R;

public class AdapterSalon  extends RecyclerView.Adapter<AdapterSalon.ViewHolderSalon>  {

    public AdapterSalon(List<Salones> listSalon) {
        this.listSalon = listSalon;
    }

    List<Salones> listSalon;
    @Override
    public ViewHolderSalon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_salon_buildings, null, false);
        return new ViewHolderSalon(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSalon holder, int position) {
        Salones salon = listSalon.get(position);
        holder.salon = salon;
        final Gson GSON = new Gson();
        holder.bind();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MainActivity.class);
                i.putExtra("JSON", GSON.toJson(holder.salon));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSalon.size();
    }

    public class ViewHolderSalon extends RecyclerView.ViewHolder {
        public Salones salon;
        TextView name;
        TextView code;
        public ViewHolderSalon(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.idSalonNombre);
            code = itemView.findViewById(R.id.idSalonCodigo);
        }
        public void bind() {
            name.setText(salon.getName());
            code.setText(salon.getCode());
        }
    }
}
