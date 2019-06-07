package sifuentes.uabcsgo.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sifuentes.uabcsgo.Directorios.FacilitiesActivity;
import sifuentes.uabcsgo.Directorios.TeachersActivity;
import sifuentes.uabcsgo.MainActivity;
import sifuentes.uabcsgo.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DirectoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DirectoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DirectoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;
    CardView cardMaestro;
    CardView cardInstalacion;
    public DirectoryFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DirectoryFragment newInstance() {
        DirectoryFragment fragment = new DirectoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_directory, container, false);
        cardMaestro = view.findViewById(R.id.idTeachers);
        cardInstalacion = view.findViewById(R.id.idFacilities);
        cardMaestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),TeachersActivity.class);
                startActivity(i);
            }
        });
        cardInstalacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),FacilitiesActivity.class);
                startActivity(i);
            }
        });
        return view;
    }

    /*public void Maestros(View view){
        Intent i = new Intent(getContext(),TeachersActivity.class);
        startActivity(i);
    }
    public void Instalaciones(View view){
        Intent i = new Intent(getContext(),FacilitiesActivity.class);
        startActivity(i);
    }*/

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
