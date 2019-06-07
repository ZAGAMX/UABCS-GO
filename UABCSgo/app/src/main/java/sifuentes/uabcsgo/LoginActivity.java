package sifuentes.uabcsgo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {
    Button buton;
    TextView registrar, email, password;

    SharedPreferences preferences;
    String userName, userDegree, userSemester, userShift;
    int userId, userSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("UABCS GO");

        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        userId = preferences.getInt("ID", 0);
        userName = preferences.getString("NAME", "");
        userDegree = preferences.getString("DEGREE", "");
        userSemester = preferences.getString("SEMESTER", "");
        userShift = preferences.getString("SHIFT", "");
        userSchedule = preferences.getInt("SCHEDULE", 0);

        if(userId > 0){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        email = findViewById(R.id.email);
        password = findViewById(R.id.contraseña);

        buton = findViewById(R.id.button);
        registrar = findViewById(R.id.idRegistrarUsuario);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String p, e;

                p = password.getText().toString().trim();
                e = email.getText().toString().trim();

                Gson gson = new GsonBuilder()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.sevuabcs.com")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                routesInterface service = retrofit.create(routesInterface.class);
                service.login(e, p).enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.isSuccessful()) {
                            //spinner.setVisibility(View.GONE);
                            final Usuario user = response.body();
                            if(user.getName() != null){
                                Toast.makeText(getBaseContext(), "Bienvenido " + user.getName(), Toast.LENGTH_LONG).show();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putInt("ID", user.getId());
                                        editor.putString("NAME", user.getName());
                                        editor.putString("DEGREE", user.getDegree());
                                        editor.putString("SEMESTER", user.getSemester());
                                        editor.putString("SHIFT", user.getShift());
                                        editor.putInt("SCHEDULE", user.getSchedule_id());
                                        editor.apply();
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                    }
                                }, 3500);
                            }else{
                                Toast.makeText(getBaseContext(), "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                            }

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
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this ,RegistroUsuarioActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
