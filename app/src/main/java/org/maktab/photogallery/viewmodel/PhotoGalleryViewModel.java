package org.maktab.photogallery.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.repository.PhotoRepository;

import java.util.List;

public class PhotoGalleryViewModel extends ViewModel {

    private final PhotoRepository mRepository;
    private final LiveData<List<GalleryItem>> mPopularItemsLiveData;

    public LiveData<List<GalleryItem>> getPopularItemsLiveData() {
        return mPopularItemsLiveData;
    }

    public PhotoGalleryViewModel() {
        mRepository = new PhotoRepository();
        mPopularItemsLiveData = mRepository.getPopularItemsLiveData();
    }

    public void fetchPopularItemsAsync() {
        mRepository.fetchItemsAsync();
    }
}
