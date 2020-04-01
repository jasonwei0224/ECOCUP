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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

/**
 * This is fragment for maps.
 */
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
        mView=inflater.inflate(R.layout.activity_maps, container, false);

        StyleableToast toast=StyleableToast.makeText(getContext(), "Cafe in Green marker with this service!", R.style.exampleToast);
        toast.show();
        //this is to receive arraylist of vendors from MainActivity.
        Bundle bundle= getArguments();
        vendors=bundle.getParcelableArrayList("arraylist");

        return mView;
    }

    //this is for google map implementation. (from Tutorial)
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

    //this is for google map implementation. (from Tutorial)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize((getContext()));
        mGoogleMap=googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //adding markers from arraylist of vendors using for loop.
        for(int i=0;i<vendors.size();i++) {
            if(vendors.get(i).getServiceProvided().equals("y")) {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(vendors.get(i).x, vendors.get(i).y)).title(vendors.get(i).name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }else {
                googleMap.addMarker(new MarkerOptions().position(new LatLng(vendors.get(i).x, vendors.get(i).y)).title(vendors.get(i).name).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

        }

        CameraPosition Liberty=CameraPosition.builder().target(new LatLng(49.238081,-122.993017)).zoom(11).bearing(0).tilt(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }

}
