package org.maktab.photogallery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.photogallery.R;
import org.maktab.photogallery.model.GalleryItem;
import org.maktab.photogallery.view.fragment.PhotoGalleryFragment;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private Context mContext;
    private List<GalleryItem> mItems;

    public List<GalleryItem> getItems() {
        return mItems;
    }

    public void setItems(List<GalleryItem> items) {
        mItems = items;
    }

    public PhotoAdapter(Context context, List<GalleryItem> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item_photo_gallery,
                parent,
                false);
        return new PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        holder.bindGalleryItem(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {

        private ImageView mImageViewItem;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);

            mImageViewItem = itemView.findViewById(R.id.item_image_view);
        }

        public void bindGalleryItem(GalleryItem item) {
            Picasso.get()
                    .load(item.getUrl())
                    .placeholder(R.mipmap.ic_android_placeholder)
                    .into(mImageViewItem);
        }
    }
}
