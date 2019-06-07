package sifuentes.uabcsgo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Notas;
import sifuentes.uabcsgo.R;

public class ActualizarNotaActivity extends AppCompatActivity {
    EditText titleTxt, descriptionTxt;
    int id;
    ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_nota);
        titleTxt = findViewById(R.id.titleActualizarNota);
        descriptionTxt = findViewById(R.id.descriptionActualizarNota);
        final Gson GSON = new Gson();
        String json = getIntent().getStringExtra("JSON");
        Notas nota = GSON.fromJson(json, Notas.class);
        setTitle(nota.getTitle());
        titleTxt.setText(nota.getTitle());
        descriptionTxt.setText(nota.getDescription());
        id = nota.getId();
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if(menuItem== R.id.menu_save_nota){
            String t = titleTxt.getText().toString();
            String d = descriptionTxt.getText().toString();

            if(t.length() == 0 || d.length() == 0) {
                Toast.makeText(getBaseContext(), "No puede haber campos vacios", Toast.LENGTH_LONG).show();
            }else {
                spinner.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.sevuabcs.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                routesInterface service = retrofit.create(routesInterface.class);
                service.putNote(id, t, d).enqueue(new Callback<Notas>() {
                    @Override
                    public void onResponse(Call<Notas> call, Response<Notas> response) {
                        if(response.isSuccessful()) {
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(), "Nota guardada", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(getBaseContext(), NotasActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 3500);
                        }else{
                            Log.e("ERROR","onResponse: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Notas> call, Throwable t) {

                    }
                });
            }
        }

        if(menuItem == R.id.menu_delete_nota) {
            spinner.setVisibility(View.VISIBLE);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.sevuabcs.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            routesInterface service = retrofit.create(routesInterface.class);
            service.deleteNote(id).enqueue(new Callback<Notas>() {
                @Override
                public void onResponse(Call<Notas> call, Response<Notas> response) {
                    if(response.isSuccessful()) {
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(getBaseContext(), "Nota eliminada", Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(getBaseContext(), NotasActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }, 3500);
                    }else{
                        Log.e("ERROR","onResponse: " + response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<Notas> call, Throwable t) {

                }
            });
        }
        return false;
    }
}
