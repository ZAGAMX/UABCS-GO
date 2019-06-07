package sifuentes.uabcsgo.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sifuentes.uabcsgo.Models.Horario;
import sifuentes.uabcsgo.Models.Maestros;
import sifuentes.uabcsgo.R;

public class AdapterHorarios  extends RecyclerView.Adapter<AdapterHorarios.ViewHolderHorarios>  {

    List<Horario> horarioList;

    public AdapterHorarios(List<Horario> listMovie) {
        this.horarioList = listMovie;
    }

    @Override
    public ViewHolderHorarios onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horario_item, null, false);

        return new AdapterHorarios.ViewHolderHorarios(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHorarios holder, int position) {
        Horario horario = horarioList.get(position);
        holder.day.setText(horario.getDay());
        holder.teacher.setText(horario.getTeacher());
        holder.subject.setText(horario.getSubject());
        holder.installment.setText(horario.getInstallment());
        holder.begin_time.setText(horario.getBegin_time());
        holder.end_time.setText(horario.getEnd_time());
    }

    @Override
    public int getItemCount() {
        return horarioList.size();
    }

    public class ViewHolderHorarios extends RecyclerView.ViewHolder {
        public TextView day;
        public TextView teacher;
        public TextView subject;
        public TextView installment;
        public TextView begin_time;
        public TextView end_time;

        public ViewHolderHorarios(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.daySchedule);
            teacher = itemView.findViewById(R.id.teacherSchedule);
            subject = itemView.findViewById(R.id.subjectSchedule);
            begin_time = itemView.findViewById(R.id.begin_timeSchedule);
            end_time = itemView.findViewById(R.id.end_timeSchedule);
            installment = itemView.findViewById(R.id.installmentSchedule);
        }
    }
}