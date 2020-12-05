package org.maktab.photogallery.data.model;

public class GalleryItem {
    private String mId;
    private String mTitle;
    private String mUrl;
    private String mOwner;
    private double mLat;
    private double mLng;

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

    public double getLat() {
        return mLat;
    }

    public void setLat(double lat) {
        mLat = lat;
    }

    public double getLng() {
        return mLng;
    }

    public void setLng(double lng) {
        mLng = lng;
    }

    public GalleryItem() {
    }

    public GalleryItem(String id, String title, String url, String owner, double lat, double lng) {
        mId = id;
        mTitle = title;
        mUrl = url;
        mOwner = owner;
        mLat = lat;
        mLng = lng;
    }
}
