package org.maktab.photogallery.network.retrofit;

import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.network.model.FlickrResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface FlickrService {

    @GET(".")
    Call<FlickrResponse> listItems(@QueryMap Map<String, String> options);
}
