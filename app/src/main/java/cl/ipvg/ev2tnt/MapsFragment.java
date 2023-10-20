package cl.ipvg.ev2tnt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import cl.ipvg.ev2tnt.Scripts.LeerDatos;


public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
            LeerDatos leerDatos = new LeerDatos();
            leerDatos.listarC();

            LatLng vehiculo= new LatLng(leerDatos.getLatitud(), leerDatos.getLongitud());
            googleMap.addMarker(new MarkerOptions().position(vehiculo).title("tu locomocion!!!"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(vehiculo));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.gmap1);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}