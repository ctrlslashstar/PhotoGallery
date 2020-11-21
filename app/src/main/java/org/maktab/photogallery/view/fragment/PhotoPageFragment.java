package org.maktab.photogallery.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.maktab.photogallery.R;
import org.maktab.photogallery.databinding.FragmentPhotoPageBinding;
import org.maktab.photogallery.receiver.ScreenReceiver;

public class PhotoPageFragment extends Fragment {

    private static final String ARG_PHOTO_PAGE_URI = "photoPageUri";

    private FragmentPhotoPageBinding mBinding;
    private Uri mPhotoPageUri;
    private ScreenReceiver mScreenReceiver;

    public PhotoPageFragment() {
        // Required empty public constructor
    }

    public static PhotoPageFragment newInstance(Uri uri) {
        PhotoPageFragment fragment = new PhotoPageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHOTO_PAGE_URI, uri);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenReceiver = new ScreenReceiver();
        if (getArguments() != null) {
            mPhotoPageUri = getArguments().getParcelable(ARG_PHOTO_PAGE_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_photo_page,
                container,
                false);

        mBinding.photoPageProgressBar.setMax(100);

        mBinding.webViewPhotoPage.getSettings().setJavaScriptEnabled(true);
        mBinding.webViewPhotoPage.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        });
        mBinding.webViewPhotoPage.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mBinding.photoPageProgressBar.setVisibility(View.GONE);
                } else {
                    mBinding.photoPageProgressBar.setVisibility(View.VISIBLE);
                    mBinding.photoPageProgressBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                activity.getSupportActionBar().setSubtitle(title);
            }
        });
        mBinding.webViewPhotoPage.loadUrl(mPhotoPageUri.toString());

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        getActivity().registerReceiver(mScreenReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();

        getActivity().unregisterReceiver(mScreenReceiver);
    }
}