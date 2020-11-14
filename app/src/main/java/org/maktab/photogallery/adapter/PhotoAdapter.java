package org.maktab.photogallery.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.maktab.photogallery.R;
import org.maktab.photogallery.data.model.GalleryItem;
import org.maktab.photogallery.databinding.ListItemPhotoGalleryBinding;
import org.maktab.photogallery.viewmodel.PhotoGalleryViewModel;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private final PhotoGalleryViewModel mViewModel;

    public PhotoAdapter(PhotoGalleryViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemPhotoGalleryBinding binding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(mViewModel.getApplication()),
                        R.layout.list_item_photo_gallery,
                        parent,
                        false);
        return new PhotoHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        GalleryItem item = mViewModel.getCurrentItems().get(position);
        holder.bindGalleryItem(item, position);
    }

    @Override
    public int getItemCount() {
        return mViewModel.getCurrentItems().size();
    }

    class PhotoHolder extends RecyclerView.ViewHolder {

        private final ListItemPhotoGalleryBinding mBinding;

        public PhotoHolder(ListItemPhotoGalleryBinding binding) {
            super(binding.getRoot());
            mBinding = binding;

            mBinding.setViewModel(mViewModel);
        }

        public void bindGalleryItem(GalleryItem item, int position) {
            mBinding.setPosition(position);

            Picasso.get()
                    .load(item.getUrl())
                    .placeholder(R.mipmap.ic_android_placeholder)
                    .into(mBinding.itemImageView);
        }
    }
}
