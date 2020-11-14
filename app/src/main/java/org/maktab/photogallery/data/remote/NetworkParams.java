package org.maktab.photogallery.data.remote;

import android.net.Uri;

import org.maktab.photogallery.data.model.GalleryItem;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {

    public static final String BASE_URL = "https://www.flickr.com/services/rest/";
    public static final String METHOD_RECENT = "flickr.photos.getRecent";
    public static final String METHOD_POPULAR = "flickr.photos.getPopular";
    public static final String METHOD_SEARCH = "flickr.photos.search";
    public static final String API_KEY = "79b5c28546b0c0fd5a0bdc65ac9eab18";

    public static final Map<String, String> BASE_OPTIONS = new HashMap<String, String>() {{
        put("api_key", API_KEY);
        put("format", "json");
        put("nojsoncallback", "1");
        put("extras", "url_s");
        put("user_id", "34427466731@N01");
    }};

    public static Map<String, String> getPopularOptions() {
        Map<String, String> popularOptions = new HashMap<>();
        popularOptions.putAll(BASE_OPTIONS);
        popularOptions.put("method", METHOD_POPULAR);

        return popularOptions;
    }

    public static Map<String, String> getSearchOptions(String query) {
        Map<String, String> searchOptions = new HashMap<>();
        searchOptions.putAll(BASE_OPTIONS);
        searchOptions.put("method", METHOD_SEARCH);
        searchOptions.put("text", query);

        return searchOptions;
    }

    public static Uri getPhotoPageUri(GalleryItem galleryItem) {
        Uri uri = Uri.parse("https://www.flickr.com/photos")
                .buildUpon()
                .appendPath(galleryItem.getOwner())
                .appendPath(galleryItem.getId())
                .build();

        return uri;
    }
}
