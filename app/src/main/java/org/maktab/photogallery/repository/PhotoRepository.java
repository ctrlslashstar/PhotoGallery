package org.maktab.photogallery.repository;

import android.util.Log;

import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.network.NetworkParams;
import org.maktab.photogallery.network.retrofit.FlickrService;
import org.maktab.photogallery.network.retrofit.RetrofitInstance;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoRepository {

    private static final String TAG = "PhotoRepository";

    private final FlickrService mFlickrService;

    public PhotoRepository() {
        Retrofit retrofit = RetrofitInstance.getInstance().getRetrofit();
        mFlickrService = retrofit.create(FlickrService.class);
    }

    //this method must run on background thread.
    public List<GalleryItem> fetchItems() {
        Call<List<GalleryItem>> call = mFlickrService.listItems(NetworkParams.POPULAR_OPTIONS);
        try {
            Response<List<GalleryItem>> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }
    }

    //this method can be run in any thread.
    public void fetchItemsAsync(Callbacks callBacks) {
        Call<List<GalleryItem>> call = mFlickrService.listItems(NetworkParams.POPULAR_OPTIONS);
        call.enqueue(new Callback<List<GalleryItem>>() {
            @Override
            public void onResponse(Call<List<GalleryItem>> call, Response<List<GalleryItem>> response) {
                List<GalleryItem> items = response.body();
                //update adapter of recyclerview
                callBacks.onItemResponse(items);
            }

            @Override
            public void onFailure(Call<List<GalleryItem>> call, Throwable t) {
                Log.e(TAG, t.getMessage(), t);
            }
        });
    }

    public interface Callbacks {
        void onItemResponse(List<GalleryItem> items);
    }
}