package org.maktab.photogallery.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.repository.PhotoRepository;
import org.maktab.photogallery.utilities.QueryPreferences;

import java.util.List;

public class PhotoGalleryViewModel extends AndroidViewModel {

    private final PhotoRepository mRepository;
    private final LiveData<List<GalleryItem>> mPopularItemsLiveData;
    private final LiveData<List<GalleryItem>> mSearchItemsLiveData;

    public LiveData<List<GalleryItem>> getPopularItemsLiveData() {
        return mPopularItemsLiveData;
    }

    public LiveData<List<GalleryItem>> getSearchItemsLiveData() {
        return mSearchItemsLiveData;
    }

    public PhotoGalleryViewModel(@NonNull Application application) {
        super(application);

        mRepository = new PhotoRepository();
        mPopularItemsLiveData = mRepository.getPopularItemsLiveData();
        mSearchItemsLiveData = mRepository.getSearchItemsLiveData();
    }

    public void fetchPopularItemsAsync() {
        mRepository.fetchPopularItemsAsync();
    }

    public void fetchSearchItemsAsync(String query) {
        mRepository.fetchSearchItemsAsync(query);
    }

    public void fetchItems() {
        String query = QueryPreferences.getSearchQuery(getApplication());
        if (query != null) {
            fetchSearchItemsAsync(query);
        } else {
            fetchPopularItemsAsync();
        }
    }

    public void setQueryInPreferences(String query) {
        QueryPreferences.setSearchQuery(getApplication(), query);
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }
}
