package sifuentes.uabcsgo.Fragments;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sifuentes.uabcsgo.Interfaces.routesInterface;
import sifuentes.uabcsgo.Models.Buildings;
import sifuentes.uabcsgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    private Marker marcador, markerEdificio;
    double lat, lng;
    View view;
    double distancia;
    List<Buildings> listBuildings = null;

    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_location, container, false);
        getActivity().setTitle("Edificios UABCS");
        setHasOptionsMenu(true);
        init();
        return view;
    }

    //Para poner boton (Toolbar)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reload, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItem = item.getItemId();
        if (menuItem == R.id.reload) {
            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            LocationManager locationManager = (LocationManager) view.getContext().getSystemService(view.getContext().LOCATION_SERVICE);
            Location locations = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            actualizar(locations);

            return true;
        }
        return false;
    }

    private void init() {
        //---------
        if (listBuildings == null) {
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
                    if (response.isSuccessful()) {
                        listBuildings = response.body();
                    } else {
                        Log.e("ERROR", "onResponse: " + response.errorBody());
                    }

                }

                @Override
                public void onFailure(Call<List<Buildings>> call, Throwable t) {
                    Log.e("ERROR", "onFailure: " + t.getMessage());
                }
            });
        }
        //---------
        mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.map, mapFragment).commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        miUbicacion();
    }

    public void agregar(double lat, double lng) {
        LatLng coordenada = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenada, 19);
        if (marcador != null) {
            marcador.remove();
        }



        marcador = mMap.addMarker(new MarkerOptions().position(coordenada).title("Ubicación actual")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));

        for (int i = 0; i<listBuildings.size();i++){
            Double v = Double.valueOf(listBuildings.get(i).getLatitude());
            Double v1 = Double.valueOf(listBuildings.get(i).getLongitude());
            LatLng edificio = new LatLng(v,v1);
            distancia = SphericalUtil.computeDistanceBetween(coordenada,edificio);
            markerEdificio = mMap.addMarker(new MarkerOptions().position(edificio).title(listBuildings.get(i).getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            if(distancia>=50){
                markerEdificio.setVisible(false);
            }
        }


        mMap.setBuildingsEnabled(true);
        mMap.animateCamera(miUbicacion);
        mMap.setMinZoomPreference(19.5f);
        mMap.setMaxZoomPreference(20.5f);
        CameraPosition cam = new CameraPosition.Builder().target(coordenada).zoom(20).tilt(70).build();
        CameraUpdate C3d = CameraUpdateFactory.newCameraPosition(cam);
        mMap.animateCamera(C3d);
        //para lockear el mapa
        mMap.getUiSettings().setScrollGesturesEnabled(false);
    }


    public void actualizar(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregar(lat, lng);
        }

    }


    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);

        LocationManager locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, true);
        Location locations = locationManager.getLastKnownLocation(provider);
        actualizar(locations);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                actualizar(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }
}
