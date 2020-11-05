package org.maktab.photogallery.repository;

import android.util.Log;

import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.network.NetworkParams;
import org.maktab.photogallery.network.model.FlickrResponse;
import org.maktab.photogallery.network.model.PhotoItem;
import org.maktab.photogallery.network.retrofit.FlickrService;
import org.maktab.photogallery.network.retrofit.RetrofitInstance;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PhotoRepository {

    private static final String TAG = "PhotoRepository";

    private Retrofit mRetrofit;
    private FlickrService mFlickrService;

    private List<GalleryItem> mItems;

    public List<GalleryItem> getItems() {
        return mItems;
    }

    public void setItems(List<GalleryItem> items) {
        mItems = items;
    }

    public PhotoRepository() {
        mRetrofit = RetrofitInstance.getInstance();
        mFlickrService = mRetrofit.create(FlickrService.class);
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

    public void fetchItemsAsync() {

    }

    /*private List<GalleryItem> parseJson(JSONObject bodyObject) throws JSONException {
        List<GalleryItem> items = new ArrayList<>();

        JSONObject photosObject = bodyObject.getJSONObject("photos");
        JSONArray photoArray = photosObject.getJSONArray("photo");

        for (int i = 0; i < photoArray.length(); i++) {
            JSONObject photoObject = photoArray.getJSONObject(i);

            if (!photoObject.has("url_s"))
                continue;

            String id = photoObject.getString("id");
            String title = photoObject.getString("title");
            String url = photoObject.getString("url_s");

            GalleryItem item = new GalleryItem(id, title, url);
            items.add(item);
        }

        return items;
    }*/
}