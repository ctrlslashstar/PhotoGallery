package org.maktab.photogallery.controller.activity;

import androidx.fragment.app.Fragment;

import org.maktab.photogallery.controller.fragment.PhotoGalleryFragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return PhotoGalleryFragment.newInstance();
    }
}