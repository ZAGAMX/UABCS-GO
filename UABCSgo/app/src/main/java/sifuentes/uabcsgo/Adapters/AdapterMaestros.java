package sifuentes.uabcsgo.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import sifuentes.uabcsgo.Models.Maestros;
import sifuentes.uabcsgo.R;

public class AdapterMaestros  extends RecyclerView.Adapter<AdapterMaestros.ViewHolderTeachers>  {

    List<Maestros> listTeachers;

    public AdapterMaestros(List<Maestros> listMovie) {
        this.listTeachers = listMovie;
    }

    @Override
    public ViewHolderTeachers onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_maestros, null, false);
        return new ViewHolderTeachers(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTeachers holder, int position) {
        Maestros teachers = listTeachers.get(position);
        holder.teachers = teachers;
        final Gson GSON = new Gson();
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return listTeachers.size();
    }

    public class ViewHolderTeachers extends RecyclerView.ViewHolder {
        public Maestros teachers;
        TextView name;
        TextView email;
        public ViewHolderTeachers(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.idProfesoresNombre);
            email = itemView.findViewById(R.id.idProfesoresCorreo);
        }

        public void bind() {
            name.setText(teachers.getName());
            email.setText(teachers.getEmail());
        }
    }
}
