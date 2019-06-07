package sifuentes.uabcsgo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sifuentes.uabcsgo.HorarioActivity;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.LoginActivity;
import sifuentes.uabcsgo.Models.Usuario;
import sifuentes.uabcsgo.NotasActivity;
import sifuentes.uabcsgo.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    TextView Notas, horario, nombre, semestre, carrera;
    SharedPreferences preferences;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preferences = getActivity().getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        Notas = view.findViewById(R.id.idNotas);
        horario = view.findViewById(R.id.idHorario);
        nombre = view.findViewById(R.id.daySchedule);
        semestre = view.findViewById(R.id.semester);
        carrera = view.findViewById(R.id.degree);
        nombre.setText(preferences.getString("NAME", ""));
        carrera.setText(preferences.getString("DEGREE", ""));
        semestre.setText(preferences.getString("SEMESTER", "") + " Semestre");
        Notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), NotasActivity.class);
                startActivity(i);
            }
        });
        horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), HorarioActivity.class);
                startActivity(i);
            }
        });
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.close_sesion,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if(menuItem== R.id.close_sesion){
            Gson gson = new GsonBuilder()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://www.sevuabcs.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            routesInterface service = retrofit.create(routesInterface.class);
            service.logout(0).enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                    if(response.isSuccessful()) {

                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        Intent i = new Intent(getActivity(), LoginActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(getActivity(), "tiempo agotado de espera", Toast.LENGTH_LONG).show();
                }
            });
            return true;
        }
        return false;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
