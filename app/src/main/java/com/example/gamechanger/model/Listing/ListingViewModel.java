package com.example.gamechanger.model.Listing;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gamechanger.model.AppRepository;
import com.example.gamechanger.model.Game.Game;

import java.util.List;

public class ListingViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Listing>> listingsList;

    public ListingViewModel(@NonNull Application application) {
        super(application);

        repository = new AppRepository(application);
        listingsList = repository.getAllListings();
    }

    public void insert (Listing listing){
        repository.insert(listing);
    }

    public void update(Listing listing){
        repository.update(listing);
    }

    public void delete(Listing listing){
        repository.delete(listing);
    }

    public LiveData<List<Listing>> getAllListings()
    {
        return listingsList;
    }

}
