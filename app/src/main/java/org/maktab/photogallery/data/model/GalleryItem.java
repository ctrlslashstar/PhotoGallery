package org.maktab.photogallery.data.model;

public class GalleryItem {
    private String mId;
    private String mTitle;
    private String mUrl;
    private String mOwner;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public GalleryItem() {
    }

    public GalleryItem(String id, String title, String url, String owner) {
        mId = id;
        mTitle = title;
        mUrl = url;
        mOwner = owner;
    }
}
