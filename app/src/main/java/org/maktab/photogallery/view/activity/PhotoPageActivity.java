package org.maktab.photogallery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.maktab.photogallery.view.fragment.PhotoPageFragment;

public class PhotoPageActivity extends SingleFragmentActivity {

    private static final String EXTRA_PHOTO_PAGE_URI = "org.maktab.photogallery.photoPageUri";

    public static Intent newIntent(Context context, Uri uri) {
        Intent intent = new Intent(context, PhotoPageActivity.class);
        intent.putExtra(EXTRA_PHOTO_PAGE_URI, uri);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        Uri photoPageUri = getIntent().getParcelableExtra(EXTRA_PHOTO_PAGE_URI);
        return PhotoPageFragment.newInstance(photoPageUri);
    }
}