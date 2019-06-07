package sifuentes.uabcsgo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import sifuentes.uabcsgo.Adapters.AdapterNotes;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Notas;

public class NotasActivity extends AppCompatActivity {
    SharedPreferences preferences;
    ArrayList<Notas> listNotas = null;
    RecyclerView recyclerView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        recyclerView = findViewById(R.id.idRecyclerNotas);
        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        fillNotes();
    }

    private void fillNotes() {
        if (listNotas == null){
            listNotas = new ArrayList<>();
            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.sevuabcs.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            routesInterface service = retrofit.create(routesInterface.class);
            Call<List<Notas>> result = service.getNotes(preferences.getInt("ID", 0));
            result.enqueue(new Callback<List<Notas>>() {
                @Override
                public void onResponse(Call<List<Notas>> call, Response<List<Notas>> response) {
                    if (response.isSuccessful()){
                        List<Notas> list = response.body();
                        /*for (int i = 0; i<list.size(); i++){
                            Notas n = list.get(i);
                            listNotas.add(new Notas(n.getTitle()));
                            Log.e("RESPUESTA",n.getTitle()+" | "+n.getDescription());
                        }
                        refresh(listNotas);
                        */
                        refresh(list);
                        Toast.makeText(getBaseContext(), list.size() + " notas", Toast.LENGTH_LONG).show();
                    }else{
                        Log.e("ERROR","onResponse: "+ response.errorBody());
                    }

                }

                @Override
                public void onFailure(Call<List<Notas>> call, Throwable t) {
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Error: Tiempo de respuesta agotado", Toast.LENGTH_SHORT);
                    toast1.show();
                    Log.e("ERROR","onFailure: "+ t.getMessage());
                }
            });


        }
    }

    public void refresh(List<Notas> listN){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        AdapterNotes adapter = new AdapterNotes(listN);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if(menuItem== R.id.menu_agregar_nota){
            Intent i = new Intent(this, AgregarNotasActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return false;
    }
}
