package sifuentes.uabcsgo.Directorios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sifuentes.uabcsgo.Adapters.AdapterMaestros;
import sifuentes.uabcsgo.Models.MaestrosSearch;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Maestros;
import sifuentes.uabcsgo.R;

public class TeachersActivity extends AppCompatActivity {

    List<Maestros> listMaestros = null;
    RecyclerView recyclerView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);
        getSupportActionBar().setTitle("MAESTROS");
        recyclerView = findViewById(R.id.idRecyclerMaestros);
        fillMaestros();
    }
    private void fillMaestros() {

        if (listMaestros == null){
            listMaestros = new ArrayList<>();
            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.sevuabcs.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            routesInterface service = retrofit.create(routesInterface.class);
            Call<List<MaestrosSearch>> result = service.getTeachers();
            result.enqueue(new Callback<List<MaestrosSearch>>() {
                @Override
                public void onResponse(Call<List<MaestrosSearch>> call, Response<List<MaestrosSearch>> response) {
                    if (response.isSuccessful()){
                        //List<Maestros> list = response.body();
                        //refresh(list);
                    }else{
                        Log.e("RESPUESTA","onResponse: "+ response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<List<MaestrosSearch>> call, Throwable t) {

                }
            });


        }
    }

    public void refresh(List<Maestros> listM){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AdapterMaestros adapter = new AdapterMaestros(listM);
        recyclerView.setAdapter(adapter);
    }
}
