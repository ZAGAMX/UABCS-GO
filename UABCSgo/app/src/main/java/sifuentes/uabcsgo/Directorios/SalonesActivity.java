package sifuentes.uabcsgo.Directorios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import sifuentes.uabcsgo.Adapters.AdapterSalon;
import sifuentes.uabcsgo.Models.Buildings;
import sifuentes.uabcsgo.Models.Salones;
import sifuentes.uabcsgo.Models.installments;
import sifuentes.uabcsgo.R;

public class SalonesActivity extends AppCompatActivity {
    //List<installments> installments;
    List<Salones> listSalon = null;
    RecyclerView recyclerView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salones);

        recyclerView = findViewById(R.id.idRecyclerSalon);
        final Gson GSON = new Gson();
        String json = getIntent().getStringExtra("JSON");

        Buildings B = GSON.fromJson(json, Buildings.class);
        List<installments> installments = B.getInstallments();
        if (listSalon == null){
            listSalon = new ArrayList<>();
            for (int i = 0; i<installments.size(); i++){
                    listSalon.add(new Salones(installments.get(i).getName(),installments.get(i).getCode()));
                //Log.e("RESPUESTA",installments.get(i).getName());
            }
        }
        if (installments.size()==0 || installments == null){
            Toast T = Toast.makeText(getApplicationContext(),"No hay aulas disponibles", Toast.LENGTH_SHORT);
            T.show();
        }else
            refresh(listSalon);
    }

    public void refresh(List<Salones> listS){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AdapterSalon adapter = new AdapterSalon(listS);
        recyclerView.setAdapter(adapter);
    }
}
