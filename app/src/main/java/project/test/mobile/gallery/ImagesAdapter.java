package project.test.mobile.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;
import project.test.mobile.R;
import project.test.mobile.models.SearchResultImage;
import project.test.mobile.utils.TextUtils;

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
        final SearchResultImage image = items.get(position);
        Logger.i("IMAGEDEBUG " + image.getLink());
        Logger.i("IMAGEDEBUG " + image.getType());
        Logger.i("IMAGEDEBUG " + image.getHeight() + " x " + image.getWidth());
        Picasso.with(context)
                .load(image.getLink())
                .transform(new CropTransformation(300, 300,
                        CropTransformation.GravityHorizontal.CENTER,
                        CropTransformation.GravityVertical.TOP))
                .into(holder.ivImageThumb);

        String idPrefix = "id: " + image.getId() + " ";
        if (TextUtils.isValidString(image.getTitle())) {
            holder.tvDescription.setText(idPrefix + image.getTitle());
        } else {
            holder.tvDescription.setText(idPrefix + "Bad Title");
        }

        // FIXME remove this
        holder.ivImageThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = "id: " + image.getId();
                Toast.makeText(context, info, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImageThumb)
        ImageView ivImageThumb;

        @BindView(R.id.tvDescription)
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
