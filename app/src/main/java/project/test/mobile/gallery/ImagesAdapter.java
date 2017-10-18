package project.test.mobile.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import project.test.mobile.R;
import project.test.mobile.models.SearchResultImage;

/**
 * Created by Mayur on 18-10-2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    ArrayList<SearchResultImage> items;
    Context context;

    public ImagesAdapter(Context context, ArrayList<SearchResultImage> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_thumbnail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SearchResultImage image = items.get(position);
        Logger.i("IMAGEDEBUG " + image.getLink());
        Logger.i("IMAGEDEBUG " + image.getType());
        Logger.i("IMAGEDEBUG " + image.getHeight() + " x " + image.getWidth());
        Picasso.with(context)
                .load(image.getLink())
                .into(holder.ivImageThumb);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImageThumb)
        ImageView ivImageThumb;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
