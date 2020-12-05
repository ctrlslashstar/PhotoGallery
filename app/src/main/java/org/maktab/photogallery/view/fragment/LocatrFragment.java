package org.maktab.photogallery.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.maktab.photogallery.R;
import org.maktab.photogallery.databinding.FragmentLocatrBinding;

import java.util.concurrent.TimeUnit;

public class LocatrFragment extends Fragment {

    private static final String TAG = "LocatrFragment";
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 0;
    private FragmentLocatrBinding mBinding;

    private FusedLocationProviderClient mFusedLocationClient;

    public LocatrFragment() {
        // Required empty public constructor
    }

    public static LocatrFragment newInstance() {
        LocatrFragment fragment = new LocatrFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_locatr,
                container,
                false);

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_locatr, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_location:
                if (hasLocationAccess()) {
                    requestLocation();
                } else {
                    //request Location access permission
                    requestLocationAccessPermission();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults == null || grantResults.length == 0)
                    return;

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    requestLocation();

                    return;
        }
    }

    private boolean hasLocationAccess() {
        boolean isFineLocation = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean isCoarseLocation = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        return isFineLocation && isCoarseLocation;
    }

    private void requestLocationAccessPermission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        requestPermissions(permissions, REQUEST_CODE_PERMISSION_LOCATION);
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        if (!hasLocationAccess())
            return;

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLocations().get(0);
                Log.d(TAG,
                        "lat: " + location.getLatitude() + ", lon: " + location.getLongitude());
            }
        };

        mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}