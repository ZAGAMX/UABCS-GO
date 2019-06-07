package sifuentes.uabcsgo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import sifuentes.uabcsgo.Fragments.DirectoryFragment;
import sifuentes.uabcsgo.Fragments.LocationFragment;
import sifuentes.uabcsgo.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportActionBar().setTitle("UBICACION");
                    fragment = new LocationFragment();
                    break;
                case R.id.navigation_directory:
                    getSupportActionBar().setTitle("DIRECTORIO");
                    fragment = new DirectoryFragment();
                    break;
                case R.id.navigation_notifications:
                    getSupportActionBar().setTitle("PERFIL");
                    fragment = new ProfileFragment();
                    break;
            }
            return selectFragment(fragment);
        }
    };


    private boolean selectFragment(Fragment homeFragment) {

        if(homeFragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_fragment,homeFragment);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectFragment(new LocationFragment());
        getSupportActionBar().setTitle("UBICACION");
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
