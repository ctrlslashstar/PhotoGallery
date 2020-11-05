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

public class PhotoRepository {

    private static final String TAG = "PhotoRepository";

    private FlickrService mFlickrService;

    private List<GalleryItem> mItems;

    public List<GalleryItem> getItems() {
        return mItems;
    }

    public void setItems(List<GalleryItem> items) {
        mItems = items;
    }

    public PhotoRepository() {
        mFlickrService = RetrofitInstance.getInstance().create(FlickrService.class);
    }

    //this method must run on background thread.
    public List<GalleryItem> fetchItems() {
        Call<FlickrResponse> call = mFlickrService.listItems(NetworkParams.POPULAR_OPTIONS);
        List<GalleryItem> items = new ArrayList<>();
        try {
            Response<FlickrResponse> response = call.execute();
            FlickrResponse flickrResponse = response.body();

            for (PhotoItem photoItem: flickrResponse.getPhotos().getPhoto()) {
                GalleryItem item = new GalleryItem(
                        photoItem.getId(),
                        photoItem.getTitle(),
                        photoItem.getUrlS());

                items.add(item);
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            return items;
        }

        /*String url = mFetcher.getPopularUrl();
        try {
            String response = mFetcher.getUrlString(url);
            Log.d(TAG, "response: " + response);

            JSONObject bodyObject = new JSONObject(response);
            List<GalleryItem> items = parseJson(bodyObject);
            return items;
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            return null;
        }*/
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