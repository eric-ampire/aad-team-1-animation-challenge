package com.andela.app.animationchallenge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.app.animationchallenge.R;
import com.andela.app.animationchallenge.model.Photo;
import com.andela.app.animationchallenge.util.ImageUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class PhotoAdapter extends ListAdapter<Photo, PhotoAdapter.PhotoViewHolder> {

    private PhotoClickListener listener;
    public PhotoAdapter(PhotoClickListener listener) {
        super(new PhotoDiff());
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_photo, parent, false);

        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo currentPhoto = getItem(position);
        ImageUtils.loadImageFromUrl(holder.photo, currentPhoto.getUrl());

        holder.itemView.setOnClickListener(v -> {
            listener.onPhotoClick(currentPhoto, v);
        });
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ownerProfile;
        ImageView photo;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.ivPhoto);
            ownerProfile = itemView.findViewById(R.id.ivUserProfile);
        }
    }

    static class PhotoDiff extends DiffUtil.ItemCallback<Photo> {

        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }

    public interface PhotoClickListener {
        void onPhotoClick(Photo photo, View view);
    }
}
