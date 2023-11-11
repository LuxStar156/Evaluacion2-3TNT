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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.ipvg.ev2tnt.Clases.Vehiculo;

public class MainUser extends AppCompatActivity {
    ListView lvehiculo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Double lat;
    Double lon;
    private List<Vehiculo> Listvehiculo = new ArrayList<Vehiculo>();
    private List<String> ListVehiculoNombre = new ArrayList();
    ArrayAdapter<Vehiculo> arrayAdapterLibro;
    ArrayAdapter<String> arrayAdapterString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        lvehiculo = (ListView) findViewById(R.id.tvListado);

        enviarDatos();

        MapsFragment mapfragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.gmapUser, mapfragment);

    }

    public void enviarDatos(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();

        databaseReference.child("Libro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Listvehiculo.clear();
                for (DataSnapshot objs : snapshot.getChildren()){
                    Vehiculo li =objs.getValue(Vehiculo.class);
                    Listvehiculo.add(li);
                    ListVehiculoNombre.add(""+li.getNombre()+" "+li.getApellido());
                    arrayAdapterString =new ArrayAdapter<String>(MainUser.this, android.R.layout.simple_expandable_list_item_1,ListVehiculoNombre);
                    lvehiculo.setAdapter(arrayAdapterString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}