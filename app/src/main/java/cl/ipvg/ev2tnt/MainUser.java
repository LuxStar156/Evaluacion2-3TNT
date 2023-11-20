package cl.ipvg.ev2tnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import cl.ipvg.ev2tnt.Clases.Vehiculo;

public class MainUser extends AppCompatActivity {
    ListView lvehiculo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private List<Vehiculo> Listvehiculo = new ArrayList<Vehiculo>();
    private List<String> ListVehiculoNombre = new ArrayList();
    ArrayAdapter<String> arrayAdapterString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        lvehiculo = (ListView) findViewById(R.id.tvListado);
        inicializarFireBase();
        leerDatos();

    }

    public void leerDatos(){
        databaseReference.child("Vehiculo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Listvehiculo.clear();
                for (DataSnapshot objs : snapshot.getChildren()){
                    Vehiculo li =objs.getValue(Vehiculo.class);
                    Listvehiculo.add(li);

                    if (li.getEstado() == true) {
                        ListVehiculoNombre.add("" + li.getNombre() + " " + li.getApellido() + ", Línea" + li.getLinea() + " (" + li.getMatricula() + ")");
                        arrayAdapterString = new ArrayAdapter<String>(MainUser.this, android.R.layout.simple_expandable_list_item_1, ListVehiculoNombre);
                        lvehiculo.setAdapter(arrayAdapterString);

                    }
                    MapsFragment mapfragment = new MapsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.gmapUser, mapfragment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();
    }
}