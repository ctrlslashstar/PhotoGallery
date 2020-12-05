package org.maktab.photogallery.data.repository;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.data.remote.NetworkParams;
import org.maktab.photogallery.data.remote.retrofit.FlickrService;
import org.maktab.photogallery.data.remote.retrofit.RetrofitInstance;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoRepository {

    private static final String TAG = "PhotoRepository";

    private final FlickrService mFlickrService;
    private final MutableLiveData<List<GalleryItem>> mPopularItemsLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<GalleryItem>> mSearchItemsLiveData = new MutableLiveData<>();

    public MutableLiveData<List<GalleryItem>> getPopularItemsLiveData() {
        return mPopularItemsLiveData;
    }

    public MutableLiveData<List<GalleryItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
    }

    public PhotoRepository() {
        Retrofit retrofit = RetrofitInstance.getInstance().getRetrofit();
        mFlickrService = retrofit.create(FlickrService.class);
    }

    //this method must run on background thread.
    public List<GalleryItem> fetchPopularItems() {
        Call<List<GalleryItem>> call = mFlickrService.listItems(NetworkParams.getPopularOptions());
        try {
            Response<List<GalleryItem>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    //this method can be run in any thread.
    public void fetchPopularItemsAsync() {
        Call<List<GalleryItem>> call =
                mFlickrService.listItems(NetworkParams.getPopularOptions());

        call.enqueue(new Callback<List<GalleryItem>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                List<GalleryItem> items = response.body();

                //update adapter of recyclerview
                mPopularItemsLiveData.setValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    //this method must run on background thread.
    public List<GalleryItem> fetchSearchItems(String query) {
        Call<List<GalleryItem>> call = mFlickrService.listItems(NetworkParams.getSearchOptions(query));
        try {
            Response<List<GalleryItem>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    public void fetchSearchItemsAsync(String query) {
        Call<List<GalleryItem>> call =
                mFlickrService.listItems(NetworkParams.getSearchOptions(query));

        call.enqueue(getSearchCallback());
    }

    public void fetchSearchItemsAsync(Location location) {
        Call<List<GalleryItem>> call =
                mFlickrService.listItems(NetworkParams.getSearchOptions(location));

        call.enqueue(getSearchCallback());
    }

    private Callback getSearchCallback() {
        return new Callback<List<GalleryItem>>() {

            //this run on main thread
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                List<GalleryItem> items = response.body();

                //update adapter of recyclerview
                mSearchItemsLiveData.setValue(items);
            }

            //this run on main thread
            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        };
    }
}