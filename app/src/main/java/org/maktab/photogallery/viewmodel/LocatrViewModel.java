package org.maktab.photogallery.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.data.repository.PhotoRepository;
import org.maktab.photogallery.view.fragment.LocatrFragment;

import java.util.List;

public class LocatrViewModel extends AndroidViewModel {

    private final PhotoRepository mRepository;
    private final LiveData<List<GalleryItem>> mSearchItemsLiveData;

    private FusedLocationProviderClient mFusedLocationClient;
    private MutableLiveData<Location> mMyLocation = new MutableLiveData<>();

    public LiveData<List<GalleryItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
    }

    public LiveData<Location> getMyLocation() {
        return mMyLocation;
    }

    public LocatrViewModel(@NonNull Application application) {
        super(application);

        mRepository = new PhotoRepository();
        mSearchItemsLiveData = mRepository.getSearchItemsLiveData();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplication());
    }

    public void fetchSearchItemsAsync(Location location) {
        mRepository.fetchSearchItemsAsync(location);
    }

    @SuppressLint("MissingPermission")
    public void requestLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLocations().get(0);
                Log.d(LocatrFragment.TAG,
                        "lat: " + location.getLatitude() + ", lon: " + location.getLongitude());

                fetchSearchItemsAsync(location);
                mMyLocation.setValue(location);
            }
        };

        mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}
