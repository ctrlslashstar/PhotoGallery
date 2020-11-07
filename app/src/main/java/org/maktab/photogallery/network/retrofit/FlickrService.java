package org.maktab.photogallery.network.retrofit;

import org.maktab.photogallery.model.GalleryItem;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface FlickrService {

    //flickr is this way exceptionally. please please do not do this for other api.
    @GET(".")
    Call<List<GalleryItem>> listItems(@QueryMap Map<String, String> options);

    //example: spotify api
    /*@GET("albums")
    Call<List<Album>> listAlbums();

    @GET("albums/{id}")
    Call<Album> getAlbum(@Path("id") String id);*/
}
