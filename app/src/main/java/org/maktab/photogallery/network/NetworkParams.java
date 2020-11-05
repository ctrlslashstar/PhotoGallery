package org.maktab.photogallery.network;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {

    public static final String BASE_URL = "https://www.flickr.com/services/rest/";
    public static final String METHOD_RECENT = "flickr.photos.getRecent";
    public static final String METHOD_POPULAR = "flickr.photos.getPopular";
    public static final String API_KEY = "79b5c28546b0c0fd5a0bdc65ac9eab18";

    public static final Map<String, String> POPULAR_OPTIONS = new HashMap<String, String>() {{
        put("method", METHOD_POPULAR);
        put("api_key", API_KEY);
        put("format", "json");
        put("nojsoncallback", "1");
        put("extras", "url_s");
        put("user_id", "34427466731@N01");
    }};
}
