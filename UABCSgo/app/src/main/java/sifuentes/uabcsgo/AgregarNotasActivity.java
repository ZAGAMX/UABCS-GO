package sifuentes.uabcsgo;

import android.content.Intent;
import android.content.SharedPreferences;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Notas;

public class AgregarNotasActivity extends AppCompatActivity {
    EditText title, description;
    ProgressBar spinner;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_notas);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);

        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_delete_notas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if(menuItem== R.id.menu_save_nota){
            String t = title.getText().toString();
            String d = description.getText().toString();

            if(t.length() == 0 || d.length() == 0) {
                Toast.makeText(getBaseContext(), "No puede haber campos vacios", Toast.LENGTH_LONG).show();
            }else {
                spinner.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.sevuabcs.com")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                routesInterface service = retrofit.create(routesInterface.class);
                service.postNotes(t, d, preferences.getInt("ID", 0)).enqueue(new Callback<Notas>() {
                    @Override
                    public void onResponse(Call<Notas> call, Response<Notas> response) {
                        if(response.isSuccessful()) {
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(), "Nota agreagada", Toast.LENGTH_LONG).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent i = new Intent(getBaseContext(), NotasActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            }, 3500);
                        }else{
                            Log.e("ERROR","onResponse: "+ response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<Notas> call, Throwable t) {

                    }
                });
            }
        }
        return false;
    }
}
