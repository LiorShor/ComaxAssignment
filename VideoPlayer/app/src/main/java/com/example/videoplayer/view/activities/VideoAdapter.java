package com.example.videoplayer.view.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoplayer.R;
import com.example.videoplayer.databinding.VideoItemBinding;
import com.example.videoplayer.model.Video;
import com.example.videoplayer.remote.DataFetching;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private final ArrayList<Video> videoArrayList;
    private final ArrayList<Video> favoriteVideosList;
    private final IVideoClick videoClick;
    private final String email;
    private Context context;

    public VideoAdapter(ArrayList<Video> videoArrayList,ArrayList<Video> favoriteVideosList, IVideoClick videoClick,String email, Context context) {
        this.videoArrayList = videoArrayList;
        this.videoClick = videoClick;
        this.favoriteVideosList = favoriteVideosList;
        this.email = email;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VideoItemBinding binding = VideoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        boolean istrue = false;
        Video video = videoArrayList.get(holder.getAdapterPosition());
        holder.binding.videoThumbNail.setImageBitmap(video.getThumbNail());
        if (favoriteVideosList.contains(video)) {
            istrue = true;
            holder.binding.favoriteImageButton.setImageResource(R.drawable.ic_in_favorite);
        }
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoClick.onVideoClick(holder.getAdapterPosition());
            }
        });
        boolean finalIstrue = istrue;
        holder.binding.favoriteImageButton.setOnClickListener(view ->
        {

            if(finalIstrue) {
                holder.binding.favoriteImageButton.setImageResource(R.drawable.ic_not_in_favorite);
            }
            else
            {
                holder.binding.favoriteImageButton.setImageResource(R.drawable.ic_in_favorite);
            }
            DataFetching dataFetching = new DataFetching(context);
            favoriteVideosList.add(video);
            dataFetching.addNewVideo(video.getName(),video.getPath(),email);
        });

    }

    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final VideoItemBinding binding;
        public ViewHolder(VideoItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface IVideoClick{
        void onVideoClick(int position);
    }
}
