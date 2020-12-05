package org.maktab.photogallery.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.data.remote.NetworkParams;
import org.maktab.photogallery.data.repository.PhotoRepository;
import org.maktab.photogallery.utilities.QueryPreferences;
import org.maktab.photogallery.view.activity.LocatrActivity;
import org.maktab.photogallery.view.activity.PhotoPageActivity;
import org.maktab.photogallery.work.PollWorker;

import java.util.ArrayList;
import java.util.List;

public class PhotoGalleryViewModel extends AndroidViewModel {

    private static final String TAG = "PhotoGalleryViewModel";
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

    public List<GalleryItem> getCurrentItems() {
        String query = QueryPreferences.getSearchQuery(getApplication());
        if (query != null && mSearchItemsLiveData.getValue() != null) {
            return mSearchItemsLiveData.getValue();
        } else if (query == null && mPopularItemsLiveData.getValue() != null) {
            return mPopularItemsLiveData.getValue();
        } else {
            return new ArrayList<>();
        }
    }

    public void setQueryInPreferences(String query) {
        QueryPreferences.setSearchQuery(getApplication(), query);
    }

    public String getQueryFromPreferences() {
        return QueryPreferences.getSearchQuery(getApplication());
    }

    public void togglePolling() {
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void startLocatr() {
        Intent intent = LocatrActivity.newIntent(getApplication());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }

    public void onImageClicked(int position) {
        GalleryItem item = getCurrentItems().get(position);
        Uri photoPageUri = NetworkParams.getPhotoPageUri(item);
        Log.d(TAG, photoPageUri.toString());

        Intent intent = PhotoPageActivity.newIntent(getApplication(), photoPageUri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);

        /*Intent intent = new Intent(Intent.ACTION_VIEW, photoPageUri);*/
    }
}
