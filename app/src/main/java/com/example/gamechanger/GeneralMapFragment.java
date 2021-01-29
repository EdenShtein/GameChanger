package com.example.gamechanger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Model;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

public class GeneralMapFragment extends Fragment {

    LinkedList<Double> latPoints = new LinkedList<>();
    LinkedList<Double> longPoints = new LinkedList<>();
    LinkedList<String> gameID = new LinkedList<>();
    private View view;

    SupportMapFragment supportMapFragment;
    GoogleMap googleMap;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_general_map, container, false);
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.general_map);

        Model.instance.getLatLongPoint(new Model.LatLongListener() {
            @Override
            public void onComplete(List<Double> latitudePoint, List<Double> longitudePoints,List<String> gameIDS) {
                latPoints.addAll(latitudePoint);
                longPoints.addAll(longitudePoints);
                gameID.addAll(gameIDS);
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        for(int i=0;i<longPoints.size();i++){
                            LatLng location = new LatLng(latPoints.get(i),longPoints.get(i));
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(location);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                            googleMap.addMarker(markerOptions);
                            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    double lat = marker.getPosition().latitude;
                                    double longitu = marker.getPosition().longitude;
                                   for(int i=0;i<gameID.size();i++)
                                   {
                                       if(latPoints.get(i)==lat && longPoints.get(i)==longitu)
                                       {

                                            Model.instance.getGameData(gameID.get(i), new Model.GameDataListener() {
                                                @Override
                                                public void onComplete(Game game) {
                                                    GeneralMapFragmentDirections.ActionGeneralMapToGameDetails action =
                                                            GeneralMapFragmentDirections.actionGeneralMapToGameDetails(
                                                                    game.getName(),game.getPrice(),game.getId(),game.getImageURL());
                                                     Navigation.findNavController(view).navigate(action);
                                                }
                                            });
                                       }
                                   }


                                    return true;
                                }
                            });

                        }
                    }
                });
            }
        });



       return view;
    }


}