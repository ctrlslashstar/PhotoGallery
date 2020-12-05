package org.maktab.photogallery.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.maktab.photogallery.view.fragment.LocatrFragment;

public class LocatrActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, LocatrActivity.class);
    }

    @Override
    public Fragment createFragment() {
        return LocatrFragment.newInstance();
    }
}