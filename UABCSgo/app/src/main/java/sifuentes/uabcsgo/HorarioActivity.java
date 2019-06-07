package sifuentes.uabcsgo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sifuentes.uabcsgo.Adapters.AdapterHorarios;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Horario;

public class HorarioActivity extends AppCompatActivity {
    SharedPreferences preferences;
    List<Horario> horarioList;
    TextView carrera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horario);
        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        RecyclerView rv =findViewById(R.id.rv);
        getSupportActionBar().setTitle("HORARIO " + preferences.getString("SEMESTER", "") + " Semestre");
        carrera = findViewById(R.id.carrera);
        carrera.setText(preferences.getString("DEGREE", ""));

        horarioList = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.sevuabcs.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        routesInterface service = retrofit.create(routesInterface.class);
        Call<List<Horario>> result = service.getHorario(preferences.getInt("SCHEDULE", 0));
        result.enqueue(new Callback<List<Horario>>() {
            @Override
            public void onResponse(Call<List<Horario>> call, Response<List<Horario>> response) {
                if (response.isSuccessful()){
                    horarioList = response.body();
                    Toast.makeText(getBaseContext(), horarioList.size() + " Materias", Toast.LENGTH_LONG).show();
                }else{
                    Log.e("ERROR","onResponse: "+ response.errorBody());
                }

            }

            @Override
            public void onFailure(Call<List<Horario>> call, Throwable t) {
                Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Tiempo de respuesta agotado", Toast.LENGTH_SHORT);
                toast1.show();
                Log.e("ERROR","onFailure: "+ t.getMessage());
            }
        });

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getBaseContext(), 1);
        rv.setLayoutManager(linearLayoutManager);
        AdapterHorarios adapter = new AdapterHorarios(horarioList);
        rv.setAdapter(adapter);
    }
}
