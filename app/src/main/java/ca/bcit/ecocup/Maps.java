package ca.bcit.ecocup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Maps extends Fragment implements OnMapReadyCallback {

    private View mView;
    GoogleMap mGoogleMap;
    MapView mMapView;
    ArrayList<Vendor> vendors;

    public Maps() {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.activity_maps_f, container, false);

        Bundle bundle= getArguments();
        vendors=bundle.getParcelableArrayList("arraylist");

        return mView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView=(MapView) mView.findViewById(R.id.mv_map_map);
        if(mMapView!=null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize((getContext()));
        mGoogleMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        for(int i=0;i<vendors.size();i++) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(vendors.get(i).x, vendors.get(i).y)).title(vendors.get(i).name));
        }

        CameraPosition Liberty=CameraPosition.builder().target(new LatLng(49.238081,-122.993017)).zoom(11).bearing(0).tilt(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }

}
