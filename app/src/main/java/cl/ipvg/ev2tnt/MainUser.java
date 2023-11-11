package cl.ipvg.ev2tnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cl.ipvg.ev2tnt.Clases.Vehiculo;

public class MainUser extends AppCompatActivity {
    TextView tvLatitud, tvLongitud, tvDireccion, lats, lons;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Double lat;
    Double lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);
        enviarDatos();



        tvLatitud = (TextView) findViewById(R.id.tVLatitudUser);
        tvLongitud = (TextView) findViewById(R.id.tVLongitudUser);
        tvDireccion = (TextView) findViewById(R.id.tVDireccionUser);
        lats = (TextView) findViewById(R.id.textView4);
        lons = (TextView) findViewById(R.id.textView5);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,},1000);

        }else {
            locationStart();
        }

    }

    public void enviarDatos(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();

        ValueEventListener Dir = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Vehiculo vehiculoL = snapshot.getValue(Vehiculo.class);
                lat = vehiculoL.getLatitud();
                lon = vehiculoL.getLongitud();

                MapsFragment mapfragment = new MapsFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.gmapUser, mapfragment);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        };

        databaseReference.addValueEventListener(Dir);

    }

    //----------------------------------------------------codigo para geolocalizacion----------------------------------------------
    private void locationStart(){
        LocationManager mlocManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        MainUser.Localizacion Local = new MainUser.Localizacion();
        Local.setMainUser(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gpsEnabled){
            Intent intentgps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intentgps);
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,(LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,(LocationListener) Local);
        tvLatitud.setText("Localización GPS");
        tvDireccion.setText("");

    }

    public void setLocation(Location loc) {

        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    tvDireccion.setText(DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        MainUser mainUser;//MainActivity mainActivity;

        public MainUser getMainUser(){
            return mainUser;
        }

        public void setMainUser(MainUser mainUser){
            this.mainUser = mainUser;
        }
        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            tvLatitud.setText(String.valueOf(loc.getLatitude()));
            tvLongitud.setText(String.valueOf(loc.getLongitude()));
            this.mainUser.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider){

            tvLatitud.setText("GPS Desactivado");

        }

        @Override
        public void onProviderEnabled(String provider){

            tvLatitud.setText("GPS Activado");

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