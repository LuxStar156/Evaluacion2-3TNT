package cl.ipvg.ev2tnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import cl.ipvg.ev2tnt.Clases.Vehiculo;

public class MainActivity extends AppCompatActivity {
    String tvLatitud, tvLongitud, tvDireccion;
    String uRut;
    final int tiempo = 5000;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        uRut = getIntent().getExtras().getString("intentID");

        inicializarFireBase();
        ejecutar();
        MapsFragment mapfragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.gmap1, mapfragment);

        //tvLatitud = (TextView) findViewById(R.id.tVLatitud);
        //tvLongitud = (TextView) findViewById(R.id.tVLongitud);
      //  tvDireccion = (TextView) findViewById(R.id.tVDireccion);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

            } else {
                    locationStart();

            }


    }

    //---------------------------------------------codigo para geolocalizacion-------------------------------------------
    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);

        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Intent intentgps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intentgps);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);
        tvLatitud = "Localización GPS";
        tvDireccion = "";

    }
    private void ejecutar(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               actualizarDatos();
                handler.postDelayed(this,5000);
            }
        },5000);
    }

    public void actualizarDatos(){
                Double userLat = Double.parseDouble(tvLatitud);
                Double userLong = Double.parseDouble(tvLongitud);

                    databaseReference.child("Vehiculo/"+uRut+"/latitud").setValue(tvLatitud);
                    databaseReference.child("Vehiculo/"+uRut+"/longitud").setValue(tvLongitud);
                    databaseReference.child("Vehiculo/"+uRut+"/direccion").setValue(tvDireccion);

    }

    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();
    }

    public void setLocation(Location loc) {

        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);

                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    tvDireccion = DirCalle.getAddressLine(0);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        MainActivity mainActivity;

        public MainActivity getMainActivity() {
            return mainActivity;
        }

        public void setMainActivity(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            tvLatitud = String.valueOf(loc.getLatitude());
            tvLongitud = String.valueOf(loc.getLongitude());
            this.mainActivity.setLocation(loc);

        }
        @Override
        public void onProviderDisabled(String provider){

            tvLatitud = "GPS Desactivado";

        }

        @Override
        public void onProviderEnabled(String provider){

            tvLatitud = "GPS Activado";

        }
        @Override
        public void onStatusChanged(String provider, int status,Bundle extras){
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;

                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;

                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }

        }



    }
}