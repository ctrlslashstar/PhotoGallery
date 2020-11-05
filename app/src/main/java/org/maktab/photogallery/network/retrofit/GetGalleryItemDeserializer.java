package org.maktab.photogallery.network.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import org.maktab.photogallery.model.GalleryItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetGalleryItemDeserializer implements JsonDeserializer<List<GalleryItem>> {

    @Override
    public List<GalleryItem> deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context) throws JsonParseException {
        List<GalleryItem> items = new ArrayList<>();

        JsonObject bodyObject = json.getAsJsonObject();
        JsonObject photosObject = bodyObject.getAsJsonObject("photos");
        JsonArray photoArray = photosObject.getAsJsonArray("photo");

        for (int i = 0; i < photoArray.size(); i++) {
            JsonObject photoObject = photoArray.get(i).getAsJsonObject();

            if (!photoObject.has("url_s"))
                continue;

            String id = photoObject.get("id").getAsString();
            String title = photoObject.get("title").getAsString();
            String url = photoObject.get("url_s").getAsString();

            GalleryItem item = new GalleryItem(id, title, url);
            items.add(item);
        }

        return items;
    }
}
