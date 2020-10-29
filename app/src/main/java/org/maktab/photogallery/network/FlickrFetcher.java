package org.maktab.photogallery.network;

import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlickrFetcher {

    private static final String TAG = "FlickrFetcher";
    public static final String BASE_URL = "https://www.flickr.com/services/rest/";
    public static final String METHOD_RECENT = "flickr.photos.getRecent";
    public static final String API_KEY = "79b5c28546b0c0fd5a0bdc65ac9eab18";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] result = outputStream.toByteArray();

            inputStream.close();
            outputStream.close();

            return result;
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public String getRecentUrl() {
         Uri uri = Uri.parse(BASE_URL)
                 .buildUpon()
                 .appendQueryParameter("method", METHOD_RECENT)
                 .appendQueryParameter("api_key", API_KEY)
                 .appendQueryParameter("format", "json")
                 .appendQueryParameter("nojsoncallback", "1")
                 .appendQueryParameter("extras", "url_s")
                 .build();

         return uri.toString();
    }
}
