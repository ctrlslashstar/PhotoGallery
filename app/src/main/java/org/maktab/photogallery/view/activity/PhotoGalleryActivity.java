package org.maktab.photogallery.view.activity;

import androidx.fragment.app.Fragment;

import org.maktab.photogallery.view.fragment.PhotoGalleryFragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}