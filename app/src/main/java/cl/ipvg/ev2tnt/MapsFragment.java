package cl.ipvg.ev2tnt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


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


public class MapsFragment extends Fragment {
    Double lat = 0.0;
    Double lon = 0.0;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {

            Bundle args = getArguments();
            Double valor1 = args.getDouble("longitud");
            Double valor2 = args.getDouble("latitud");

            lat = valor1;
            lon = valor2;

            LatLng vehiculo= new LatLng(lat, lon);
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