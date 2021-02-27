package com.example.gamechanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Model;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    static int add_flag = 0;
    static int edit_flag = 0;
    private View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         view =  inflater.inflate(R.layout.fragment_maps, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.general_map);
        setHasOptionsMenu(true);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        if(ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
            /*client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if(location!=null)
                    {
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(latLng).title("You current location"));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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


                                        if (getAdd_flag() == 1){
                                            setAdd_flag(0);
                                            MapsFragmentDirections.ActionMapsToAddGame action_add = MapsFragmentDirections.actionMapsToAddGame(latLng.latitude,latLng.longitude);
                                            AddGameFragment addGameFragment = new AddGameFragment();
                                            addGameFragment.setMapFlag(1);
                                            Navigation.findNavController(view).navigate(action_add);
                                        }
                                        if (getEdit_flag() == 1){
                                            setEdit_flag(0);
                                            String title = MapsFragmentArgs.fromBundle(getArguments()).getGameTitle();
                                            String price = MapsFragmentArgs.fromBundle(getArguments()).getGamePrice();
                                            String Id = MapsFragmentArgs.fromBundle(getArguments()).getGameId();
                                            MapsFragmentDirections.ActionMapsToEditGame action = MapsFragmentDirections.actionMapsToEditGame(title,price,null,Id,latLng.latitude,latLng.longitude);
                                            EditGameFragment editGameFragment =  new EditGameFragment();
                                            editGameFragment.setMap_flag(1);
                                            Navigation.findNavController(view).navigate(action);
                                        }

                                    }
                                });
                            }

                        });
                    }
                }
            });*/
        }
        else {
            ActivityCompat.requestPermissions(getActivity(),
                   new String[]{Manifest.permission.ACCESS_FINE_LOCATION} ,44);
        }

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }

                return false;
            }
        });

        return view;
    }

    private void getCurrentLocation() {
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            /*googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                                @Override
                                public void onMapLongClick(LatLng latLng) {
                                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());

                                    String address = "";

                                    try {

                                        List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);

                                        if (listAddresses != null && listAddresses.size() > 0) {
                                            if (listAddresses.get(0).getThoroughfare() != null) {
                                                if (listAddresses.get(0).getSubThoroughfare() != null) {
                                                    address += listAddresses.get(0).getSubThoroughfare() + " ";
                                                }
                                                address += listAddresses.get(0).getThoroughfare();
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
                            });*/
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("You current location"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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

                                    if (getAdd_flag() == 1) {
                                        setAdd_flag(0);
                                        String title = MapsFragmentArgs.fromBundle(getArguments()).getGameTitle();
                                        String price = MapsFragmentArgs.fromBundle(getArguments()).getGamePrice();
                                        MapsFragmentDirections.ActionMapsToAddGame action_add = MapsFragmentDirections.actionMapsToAddGame(latLng.latitude,latLng.longitude, title, price);
                                        AddGameFragment addGameFragment = new AddGameFragment();
                                        addGameFragment.setMapFlag(1);
                                        Navigation.findNavController(view).navigate(action_add);
                                    }

                                    if (getEdit_flag() == 1) {
                                        setEdit_flag(0);
                                        String title = MapsFragmentArgs.fromBundle(getArguments()).getGameTitle();
                                        String price = MapsFragmentArgs.fromBundle(getArguments()).getGamePrice();
                                        String Id = MapsFragmentArgs.fromBundle(getArguments()).getGameId();
                                        String gameImg = MapsFragmentArgs.fromBundle(getArguments()).getImgUrl();
                                        MapsFragmentDirections.ActionMapsToEditGame action_edit = MapsFragmentDirections.actionMapsToEditGame(title,price,gameImg,Id,latLng.latitude,latLng.longitude);
                                        EditGameFragment editGameFragment =  new EditGameFragment();
                                        editGameFragment.setMap_flag(1);
                                        Navigation.findNavController(view).navigate(action_edit);
                                    }
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
            if(requestCode==44) {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
            }
    }

    public int getAdd_flag() {
        return add_flag;
    }

    public void setAdd_flag(int add_flag) {
        MapsFragment.add_flag = add_flag;
    }

    public int getEdit_flag() {
        return edit_flag;
    }

    public void setEdit_flag(int edit_flag) {
        MapsFragment.edit_flag = edit_flag;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.map_menu, menu);
        MenuItem back_btn = menu.findItem(R.id.map_menu_back);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_menu_back:
                if(view != null) {
                    Navigation.findNavController(view).popBackStack();
                }
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }
}