package org.maktab.photogallery.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import org.maktab.photogallery.controller.fragment.PhotoGalleryFragment;
import org.maktab.photogallery.network.FlickrFetcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThumbnailDownloader<T> extends HandlerThread {

    public static final String TAG = "ThumbnailDownloader";
    private static final int WHAT_THUMBNAIL_DOWNLOAD = 1;

    private Handler mHandlerRequest;
    private Handler mHandlerResponse;
    private ConcurrentHashMap<T, String> mRequestMap = new ConcurrentHashMap<>();

    public Handler getHandlerResponse() {
        return mHandlerResponse;
    }

    public void setHandlerResponse(Handler handlerResponse) {
        mHandlerResponse = handlerResponse;
    }

    public ThumbnailDownloader() {
        super(TAG);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        mHandlerRequest = new Handler() {
            //run message
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                //download url from net
                try {
                    handleDownloadMessage(msg);
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        };
    }

    private void handleDownloadMessage(@NonNull Message msg) throws IOException {
        if (msg.what == WHAT_THUMBNAIL_DOWNLOAD) {
            if (msg.obj == null)
                return;

            T target = (T) msg.obj;
            String url = mRequestMap.get(target);

            FlickrFetcher flickrFetcher = new FlickrFetcher();
            byte[] bitmapBytes = flickrFetcher.getUrlBytes(url);

            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);

            mHandlerResponse.post(new Runnable() {
                @Override
                public void run() {
                    if (mRequestMap.get(target) != url)
                        return;

                    if (target instanceof PhotoGalleryFragment.PhotoHolder) {
                        PhotoGalleryFragment.PhotoHolder photoHolder = (PhotoGalleryFragment.PhotoHolder) target;
                        photoHolder.bindBitmap(bitmap);
                    }
                }
            });
        }
    }

    public void queueThumbnail(T target, String url) {
        mRequestMap.put(target, url);

        //create a message and send it to looper (to put in queue)
        Message message = mHandlerRequest.obtainMessage(WHAT_THUMBNAIL_DOWNLOAD, target);
        message.sendToTarget();
    }
}
