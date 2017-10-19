package project.test.mobile.gallery;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import project.test.mobile.BaseActivity;
import project.test.mobile.R;
import project.test.mobile.models.SearchResultImage;
import project.test.mobile.utils.EndlessRecyclerViewScrollListener;
import project.test.mobile.utils.Injection;

/**
 * Created by Mayur on 17-10-2017.
 */

public class GalleryActivity extends BaseActivity
        implements GalleryActivityContract.GalleryView {

    @BindView(R.id.rvImages)
    RecyclerView rvImages;

    @BindView(R.id.ivProfile)
    ImageView ivProfile;

    @BindView(R.id.tvTimer)
    TextView tvTimer;

    private ImagesAdapter imagesAdapter;
    private GridLayoutManager layoutManager;
    private GalleryActivityContract.GalleryPresenter presenter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private CountDownTimer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        new GalleryPresenterImpl(this,
                Injection.provideSchedulerProvider(),
                Injection.providesRepository(this));
    }

    @Override
    public void initView() {
        Logger.i("gallery initView and get images");
        Picasso.with(context)
                .load(R.drawable.octocat)
                .resize(100, 100)
                .transform(new CropCircleTransformation())
                .into(ivProfile, new Callback() {
                    @Override
                    public void onSuccess() {
                        ivProfile.setBackground(ContextCompat
                                .getDrawable(context, R.drawable.bg_circle));
                    }

                    @Override
                    public void onError() {

                    }
                });
        setupImages();
        presenter.getImages(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer = new CountDownTimer(1000000000, 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                Calendar c = Calendar.getInstance();
                tvTimer.setText(c.get(Calendar.HOUR) +
                        ":" + c.get(Calendar.MINUTE) +
                        ":" + c.get(Calendar.SECOND) +
                        ":" + c.get(Calendar.MILLISECOND));
            }

            @Override
            public void onFinish() {

            }
        };

        timer.start();
    }

    @Override
    public void setPresenter(GalleryActivityContract.GalleryPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private void setupImages() {
        imagesAdapter = new ImagesAdapter(this, new ArrayList<SearchResultImage>());
        layoutManager = new GridLayoutManager(this, 2);
        rvImages.setAdapter(imagesAdapter);
        rvImages.setLayoutManager(layoutManager);
        rvImages.setHasFixedSize(true);
        rvImages.setItemViewCacheSize(40);
        rvImages.setDrawingCacheEnabled(true);
        rvImages.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        rvImages.setNestedScrollingEnabled(false);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Logger.i("loaded page " + Integer.toString(page));
                presenter.getImages(page + 1);
            }
        };
        rvImages.addOnScrollListener(scrollListener);
    }

    @Override
    public void showImages(ArrayList<SearchResultImage> images) {
        Logger.i("showing images");
        ArrayList<SearchResultImage> cleanImages = new ArrayList<>();

        for (SearchResultImage image : images) {
            if (image.getType() == null) {
                ArrayList<SearchResultImage> subImages = image.getImages();

                // if image type == null
                // display an image from subImages
                if (subImages != null && subImages.size() > 0) {
                    SearchResultImage image1 = subImages.get(0);
                    cleanImages.add(image1);
                }
            }
        }

        int position = imagesAdapter.getItemCount();
        imagesAdapter.addItems(cleanImages);
        imagesAdapter.notifyDataSetChanged();
        Logger.i("RECYCDEBUG position " + position);
//        imagesAdapter.notifyItemRangeChanged(position, cleanImages.size());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }
}
