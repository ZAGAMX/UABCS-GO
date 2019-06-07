package sifuentes.uabcsgo.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import sifuentes.uabcsgo.Directorios.SalonesActivity;
import sifuentes.uabcsgo.Models.Buildings;
import sifuentes.uabcsgo.R;

public class AdapterBuildings extends RecyclerView.Adapter<AdapterBuildings.ViewHolderBuildings>   {

    List<Buildings> listBuildings;

    public AdapterBuildings(List<Buildings> listBuildings) {
        this.listBuildings = listBuildings;
    }

    @Override
    public ViewHolderBuildings onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_all_buildings, null, false);
        return new ViewHolderBuildings(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderBuildings holder, int position) {

        Buildings buildings = listBuildings.get(position);
        holder.buildings = buildings;
        final Gson GSON = new Gson();
        holder.bind();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), SalonesActivity.class);
                i.putExtra("JSON", GSON.toJson(holder.buildings));
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBuildings.size();
    }

    public class ViewHolderBuildings extends RecyclerView.ViewHolder {
        public Buildings buildings;
        ImageView image;
        TextView name;
        TextView description;
        TextView schedule;
        public ViewHolderBuildings(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.idBuildingsImg);
            name = itemView.findViewById(R.id.idBuildingsNombre);
            description = itemView.findViewById(R.id.idBuildingsDescripcion);
            schedule = itemView.findViewById(R.id.idBuildingsSchedule);
        }

        public void bind() {
            Picasso.get().load(buildings.getImage()).into(image);
            name.setText(buildings.getName());
            description.setText(buildings.getDescription());
            schedule.setText(buildings.getSchedule());
        }
    }
}
