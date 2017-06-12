package com.example.imageloader;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.example.imageloadaerlibrary.config.ScaleMode;
import com.example.imageloadaerlibrary.loader.ImageLoader;

import java.util.List;

/**
 * author : daiwenbo
 * e-mail : daiwwenb@163.com
 * date   : 2017/6/12
 * description   : xxxx描述
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<String> images;
    private Context context;
    private final ViewPropertyAnimation.Animator animationObject;

    public ImageAdapter(Context context, List<String> images) {
        this.images = images;
        this.context = context;
        animationObject = new ViewPropertyAnimation.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);
                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(2500);
                fadeAnim.start();
            }
        };
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_image, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder holder, int position) {
        String url = images.get(position);
        ImageLoader.with(context)
                .url(url)
                .thumbnail(1.0f)
                .animate(animationObject)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .scale(ScaleMode.CENTER_CROP)
                .into(holder.imageView);
        holder.textView.setText(position + "");
    }

    @Override
    public int getItemCount() {
        return images.size() > 0 ? images.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.tv_num);
        }
    }
}
