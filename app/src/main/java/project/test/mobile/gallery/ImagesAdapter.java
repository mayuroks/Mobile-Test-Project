package project.test.mobile.gallery;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import project.test.mobile.R;
import project.test.mobile.gallery.holders.VH_Image;
import project.test.mobile.gallery.holders.VH_Placeholder;
import project.test.mobile.models.SearchResultImage;

/**
 * Created by Mayur on 18-10-2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SearchResultImage> items;
    private Context context;
    private static final int VIEW_TYPE_IMAGE = 0;
    private static final int VIEW_TYPE_LOADER = 1;

    public ImagesAdapter(Context context, ArrayList<SearchResultImage> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_IMAGE) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_thumbnail, parent, false);
            return new VH_Image(v);
        } else if (viewType == VIEW_TYPE_LOADER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_placeholder, parent, false);
            return new VH_Placeholder(v);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VH_Image) {
            final SearchResultImage image = items.get(position);
            VH_Image viewHolder = (VH_Image) holder;
            Logger.i("IMAGEDEBUG " + image.getLink());
            Logger.i("IMAGEDEBUG " + image.getType());
            Logger.i("IMAGEDEBUG " + image.getHeight() + " x " + image.getWidth());

            Uri uri = Uri.parse(image.getLink());
            // FIXME load all webp images
//            String url = "http://thoughtrelics.com/thumbs/thumbnail_" +
//                    Integer.toString(position + 1) + "_q25.webp";
//            Uri uri = Uri.parse(url);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                    .setResizeOptions(new ResizeOptions(300, 300))
                    .build();

            viewHolder.sdvThumbnail.setController(Fresco.newDraweeControllerBuilder()
                    .setOldController(viewHolder.sdvThumbnail.getController())
                    .setTapToRetryEnabled(true)
                    .setImageRequest(request)
                    .setControllerListener(new ControllerListener<ImageInfo>() {
                        @Override
                        public void onSubmit(String id, Object callerContext) {
                        }

                        @Override
                        public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        }

                        @Override
                        public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                        }

                        @Override
                        public void onIntermediateImageFailed(String id, Throwable throwable) {
                        }

                        @Override
                        public void onFailure(String id, Throwable throwable) {
                        }

                        @Override
                        public void onRelease(String id) {
                        }
                    })
                    .build());

            viewHolder.sdvThumbnail
                    .getHierarchy()
                    .setProgressBarImage(new ProgressBarDrawable());

        } else if (holder instanceof VH_Placeholder) {
            VH_Placeholder viewHolder = (VH_Placeholder) holder;
            viewHolder.shimmer1.startShimmerAnimation();
            viewHolder.shimmer2.startShimmerAnimation();
            viewHolder.shimmer3.startShimmerAnimation();
            viewHolder.shimmer4.startShimmerAnimation();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @UiThread
    public void addItems(ArrayList<SearchResultImage> items) {
        this.items.addAll(items);
    }

    @UiThread
    public void addItem(SearchResultImage item) {
        this.items.add(item);
    }

    @UiThread
    public void removeItem(int position) {
        this.items.remove(position);
    }

    @UiThread
    public void removePlaceholders() {
        ArrayList<Integer> images = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            SearchResultImage image = items.get(i);
            if (image == null) {
                images.add(i);
            }
        }

        for (int i = 0; i < images.size(); i++) {
            items.remove(i);
        }
    }

    public SearchResultImage getLastItem() {
        return items.size() > 0 ? items.get(items.size() - 1) : null;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADER : VIEW_TYPE_IMAGE;
    }

    public boolean isPlaceholder(int position) {
        SearchResultImage image = items.get(position);
        return image == null;
    }
}
