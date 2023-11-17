package cl.ipvg.ev2tnt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Locale;

import cl.ipvg.ev2tnt.Clases.Vehiculo;

public class Registro extends AppCompatActivity {

    EditText etNombre, etApellido, etMatricula, etMarca, etModelo,etLinea;
    String Latitud, Longitud, Direccion;
    Button btRegis;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        inicializarFireBase();
        btRegis = (Button) findViewById(R.id.btRegistrar);
        etNombre = (EditText) findViewById(R.id.editTextNombre);
        etApellido = (EditText) findViewById(R.id.editTextApellido);
        etMatricula = (EditText) findViewById(R.id.editTextMatricula);
        etMarca = (EditText) findViewById(R.id.editTextMarca);
        etModelo = (EditText) findViewById(R.id.editTextModelo);
        etLinea = (EditText) findViewById(R.id.editTextLinea);

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,},1000);

        }else {
            locationStart();
        }

        btRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double lat = Double.parseDouble(Latitud);
                Double lon = Double.parseDouble(Longitud);
                Vehiculo vehiculo = new Vehiculo();

                vehiculo.setId(UUID.randomUUID().toString());
                vehiculo.setNombre(etNombre.getText().toString());
                vehiculo.setApellido(etApellido.getText().toString());
                vehiculo.setMatricula(etMatricula.getText().toString());
                vehiculo.setMarca(etMarca.getText().toString());
                vehiculo.setModelo(etModelo.getText().toString());
                vehiculo.setLinea(etLinea.getText().toString());
                vehiculo.setLatitud(lat);
                vehiculo.setLongitud(lon);
                vehiculo.setDireccion(Direccion);


                try {
                    databaseReference.child("Vehiculo").child(vehiculo.getId()).setValue(vehiculo);
                    Snackbar.make(findViewById(R.id.snackbar_RegistroTrue), "Sus datos fueron registrados exitosamente", Snackbar.LENGTH_SHORT).show();
                }catch (UnsupportedOperationException e){
                    Snackbar.make(findViewById(R.id.snackbar_RegistroTrue), "ERROR, Sus datos no fueron registrados", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    //---------------------------------------------------codigo para geolocalizacion------------------------------------------------
    private void locationStart(){
        LocationManager mlocManager =(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Registro.Localizacion Local = new Registro.Localizacion();//MainUser.Localizacion Local = new MainUser.Localizacion();
        Local.setRegistro(this);

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
        Latitud = "Localización GPS";
        Direccion = "";

    }

    public void setLocation(Location loc) {

        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    Direccion = DirCalle.getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class Localizacion implements LocationListener {
        Registro registro;//MainActivity mainActivity;

        public Registro getRegistro() {
            return registro;
        }

        public void setRegistro(Registro registro) {
            this.registro = registro;
        }

        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            Latitud = (String.valueOf(loc.getLatitude()));
            Longitud = (String.valueOf(loc.getLongitude()));
            this.registro.setLocation(loc);
        }
        @Override
        public void onProviderDisabled(String provider){

            Latitud = "GPS Desactivado";

        }

        @Override
        public void onProviderEnabled(String provider){

            Latitud = "GPS Activado";

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