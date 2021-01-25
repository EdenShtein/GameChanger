package com.example.gamechanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsFragment extends Fragment  {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    GoogleMap googleMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_maps, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if(ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            getCurrentLocation();
        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(),
                   new String[]{Manifest.permission.ACCESS_FINE_LOCATION} ,44);
        }
        return view;
    }


    private void getCurrentLocation() {

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null)
                {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                @Override
                                public void onMapLongClick(LatLng latLng) {
                                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());

                                    String address = "";

                                    try {

                                        List<Address> listAdddresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

                                        if (listAdddresses != null && listAdddresses.size() > 0) {
                                            if (listAdddresses.get(0).getThoroughfare() != null) {
                                                if (listAdddresses.get(0).getSubThoroughfare() != null) {
                                                    address += listAdddresses.get(0).getSubThoroughfare() + " ";
                                                }
                                                address += listAdddresses.get(0).getThoroughfare();
                                            }
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if (address.equals("")) {
                                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyy-MM-dd");
                                        address += sdf.format(new Date());
                                    }

                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(latLng);
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                    googleMap.addMarker(markerOptions);
                                }
                            });
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(LatLng latLng) {
                                    MarkerOptions markerOptions = new MarkerOptions();
                                    markerOptions.position(latLng);
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                    markerOptions.title(latLng.latitude + " : "+ latLng.longitude);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                            latLng,7
                                    ));
                                    googleMap.addMarker(markerOptions);
                                }
                            });
                        }

                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(requestCode==44)
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    getCurrentLocation();
                }
            }
    }


}