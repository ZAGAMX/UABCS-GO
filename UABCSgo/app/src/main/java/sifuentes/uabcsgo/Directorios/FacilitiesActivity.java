package sifuentes.uabcsgo.Directorios;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import sifuentes.uabcsgo.Adapters.AdapterBuildings;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Buildings;
import sifuentes.uabcsgo.R;

public class FacilitiesActivity extends AppCompatActivity {

    List<Buildings> listBuildings = null;
    RecyclerView recyclerView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facilities);
        getSupportActionBar().setTitle("INSTALACIONES");

        recyclerView = findViewById(R.id.idRecyclerBuildings);
        fillBuildings();
    }

    private void fillBuildings() {

        if (listBuildings == null){
            listBuildings = new ArrayList<>();
            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.sevuabcs.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            routesInterface service = retrofit.create(routesInterface.class);
            Call<List<Buildings>> result = service.getBuildings();
            result.enqueue(new Callback<List<Buildings>>() {
                @Override
                public void onResponse(Call<List<Buildings>> call, Response<List<Buildings>> response) {
                    if (response.isSuccessful()){
                        List<Buildings> list = response.body();
                        refresh(list);

                    }else{
                        Log.e("ERROR","onResponse: "+ response.errorBody());
                    }

                }

                @Override
                public void onFailure(Call<List<Buildings>> call, Throwable t) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Tiempo de respuesta agotado", Toast.LENGTH_SHORT);
                    toast1.show();
                    Log.e("ERROR","onFailure: "+ t.getMessage());
                }
            });


        }
    }

    public void refresh(List<Buildings> listB){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AdapterBuildings adapter = new AdapterBuildings(listB);
        recyclerView.setAdapter(adapter);
    }
}
