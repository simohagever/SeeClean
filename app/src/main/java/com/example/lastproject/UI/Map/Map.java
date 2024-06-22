package com.example.lastproject.UI.Map;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lastproject.DB.FireBaseHelper;
import com.example.lastproject.R;
import com.example.lastproject.UI.SelectReport.SelectNewReport;
import com.example.lastproject.UI.homePage.HomePage;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.LinkedList;


/**
 * The Map activity displays the map and handles location-related functionality.
 */
public class Map extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment supportMapFragment;
    LatLng latLng;
    Button backHome;
    ModuleMap module;
    GoogleMap mMap;
    FusedLocationProviderClient fusedLocationClient;

    /**
     * onCreate method is called when the activity is starting.
     *
     * @param savedInstanceState a Bundle object containing the activity's previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Initialize SupportMapFragment
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        supportMapFragment.getMapAsync(this);

        // Initialize ModuleMap
        module = new ModuleMap(this);

        // Initialize backHome button and set OnClickListener
        backHome = findViewById(R.id.btnback);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Map.this, HomePage.class));
            }
        });

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    /**
     * getLastLocation method gets the last known location of the device and updates the map accordingly.
     */
    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (!mMap.isMyLocationEnabled()) {
            mMap.setMyLocationEnabled(true);
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                if (getIntent().hasExtra("NewReport") && getIntent().getExtras().getBoolean("NewReport")) {
                    module.AddMarker(getIntent().getExtras().getString("Description"), getIntent().getExtras().getString("Time"), getIntent().getExtras().getString("Date"), (Bitmap) getIntent().getParcelableExtra("Picture"), latLng, new FireBaseHelper.markerAdded() {
                        @Override
                        public void onMarkerAdded() {
                            module.PlaceMarkers(mMap, new FireBaseHelper.markerCreated() {
                                @Override
                                public void onMarkersCreated(LinkedList<Marker> list) {
                                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                        @Override
                                        public boolean onMarkerClick(@NonNull Marker marker) {
                                            Intent intent = new Intent(Map.this, SelectNewReport.class);
                                            intent.putExtra("id", marker.getTag().toString());
                                            startActivity(intent);
                                            return false;
                                        }
                                    });
                                }
                            });
                        }
                    });
                } else {
                    module.PlaceMarkers(mMap, new FireBaseHelper.markerCreated() {
                        @Override
                        public void onMarkersCreated(LinkedList<Marker> list) {
                            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(@NonNull Marker marker) {
                                    Intent intent = new Intent(Map.this, SelectNewReport.class);
                                    intent.putExtra("id", marker.getTag().toString());
                                    startActivity(intent);
                                    return false;
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    /**
     * onMapReady method is called when the map is ready to be used.
     *
     * @param googleMap a GoogleMap object representing the map
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getLastLocation();
    }
}
