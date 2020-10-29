package org.maktab.photogallery.repository;

import org.maktab.photogallery.model.GalleryItem;

import java.util.ArrayList;
import java.util.List;

public class PhotoRepository {

    private List<GalleryItem> mItems = new ArrayList<>();

    public List<GalleryItem> getItems() {
        return mItems;
    }

    public void setItems(List<GalleryItem> items) {
        mItems = items;
    }
}
