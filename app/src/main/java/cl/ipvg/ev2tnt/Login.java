package cl.ipvg.ev2tnt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cl.ipvg.ev2tnt.Clases.Vehiculo;

public class Login extends AppCompatActivity {
    Button btAceptar;
    EditText etRut,etContra;
    TextView tvError;
    Intent intent;
    Boolean aceptado;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarFireBase();
        intent = new Intent(this,MainActivity.class);
        btAceptar = (Button) findViewById(R.id.btMain);
        etRut = (EditText) findViewById(R.id.editTextRutLogin);
        etContra = (EditText) findViewById(R.id.editTextContra1);
        tvError = (TextView) findViewById(R.id.tvErrorDatos);

        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Vehiculo").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot objs : snapshot.getChildren()){
                            Vehiculo li =objs.getValue(Vehiculo.class);

                            if(etRut.getText().toString().equals(li.getRut()) && etContra.getText().toString().equals(li.getContrasena())){
                                //aceptado = true;
                                databaseReference.child("Vehiculo/"+etRut.getText().toString()+"/estado").setValue(aceptado);
                                intent.putExtra("intentID",etRut.getText().toString());
                                startActivity(intent);
                            }else{
                                aceptado = false;
                                tvError.setText("No coinciden los campos");
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference();
    }
}