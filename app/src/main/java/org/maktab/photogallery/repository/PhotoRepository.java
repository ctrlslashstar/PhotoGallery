package org.maktab.photogallery.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.network.NetworkParams;
import org.maktab.photogallery.network.retrofit.FlickrService;
import org.maktab.photogallery.network.retrofit.RetrofitInstance;

import java.io.IOException;
import java.util.HashMap;
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
    public List<GalleryItem> fetchItems() {
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

    public void fetchSearchItemsAsync(String query) {
        Call<List<GalleryItem>> call =
                mFlickrService.listItems(NetworkParams.getSearchOptions(query));

        call.enqueue(new Callback<List<GalleryItem>>() {

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
        });
    }
}