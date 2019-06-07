package sifuentes.uabcsgo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Usuario;

public class RegistroUsuarioActivity extends AppCompatActivity {
    Spinner option1;
    Spinner option2;
    Spinner option3;
    Button boton;
    TextView nombre, correo, contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        option1 = findViewById(R.id.semestre);
        option2 = findViewById(R.id.carrera);
        option3 = findViewById(R.id.turno);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.semestre, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.carrera, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.turno, android.R.layout.simple_spinner_item);
        option1.setAdapter(adapter1);
        option2.setAdapter(adapter2);
        option3.setAdapter(adapter3);
        nombre = findViewById(R.id.nombre);
        correo = findViewById(R.id.email);
        contraseña = findViewById(R.id.contraseña);

        boton = findViewById(R.id.button);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nombre.getText().toString().trim();
                String email = correo.getText().toString().trim();
                String pass = contraseña.getText().toString().trim();
                int semester = option1.getSelectedItemPosition();
                int degree = option2.getSelectedItemPosition();
                int shift = option3.getSelectedItemPosition();

                int[] degrees = { 0, 1, 2, 3 };

                if(name.length() == 0 || email.length() == 0 || pass.length() == 0 || semester == 0 || degree == 0 || shift == 0) {
                    Toast.makeText(getBaseContext(), "No puede haber campos vacíos", Toast.LENGTH_LONG).show();
                }else{
                    if(degree == 3 && shift == 2) {
                        Toast.makeText(getBaseContext(), "LATI solo disponible en el turno vespertino", Toast.LENGTH_LONG).show();
                    }else{
                        if(shift == 1) {
                            Gson gson = new GsonBuilder()
                                    .create();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://www.sevuabcs.com")
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .build();
                            routesInterface service = retrofit.create(routesInterface.class);
                            service.register(name, email, pass, "Vespertino", 2, degrees[degree]).enqueue(new Callback<Usuario>() {
                                @Override
                                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                    if(response.isSuccessful()) {
                                        Toast.makeText(getBaseContext(), "Usuario registrado", Toast.LENGTH_LONG).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent i = new Intent(RegistroUsuarioActivity.this, LoginActivity.class);
                                                startActivity(i);
                                            }
                                        }, 3500);
                                    }else{
                                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Usuario> call, Throwable t) {
                                    Toast.makeText(getBaseContext(), "tiempo agotado de espera", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            Gson gson = new GsonBuilder()
                                    .create();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://www.sevuabcs.com")
                                    .addConverterFactory(GsonConverterFactory.create(gson))
                                    .build();
                            routesInterface service = retrofit.create(routesInterface.class);
                            service.register(name, email, pass, "Matutino", 1, degrees[degree]).enqueue(new Callback<Usuario>() {
                                @Override
                                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                                    if(response.isSuccessful()) {
                                        Toast.makeText(getBaseContext(), "Usuario registrado", Toast.LENGTH_LONG).show();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent i = new Intent(RegistroUsuarioActivity.this, LoginActivity.class);
                                                startActivity(i);
                                            }
                                        }, 3500);
                                    }else{
                                        Toast.makeText(getBaseContext(), "error", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Usuario> call, Throwable t) {
                                    Toast.makeText(getBaseContext(), "tiempo agotado de espera", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }
            }
        });
    }
}
