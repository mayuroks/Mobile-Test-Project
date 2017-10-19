package project.test.mobile.gallery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import project.test.mobile.BaseActivity;
import project.test.mobile.R;
import project.test.mobile.models.SearchResultImage;
import project.test.mobile.utils.EndlessRecyclerViewScrollListener;
import project.test.mobile.utils.Injection;
import project.test.mobile.utils.TextUtils;

/**
 * Created by Mayur on 17-10-2017.
 */

public class GalleryActivity extends BaseActivity
        implements GalleryActivityContract.GalleryView {

    private static final String FULL_NAME = "fullName";

    @BindView(R.id.rvImages)
    RecyclerView rvImages;

    @BindView(R.id.tvTimer)
    TextView tvTimer;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvErrorMsg)
    TextView tvErrorMsg;

    @BindView(R.id.avLoader)
    AVLoadingIndicatorView avLoader;

    private ImagesAdapter imagesAdapter;
    private GridLayoutManager layoutManager;
    private GalleryActivityContract.GalleryPresenter presenter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private CountDownTimer timer;

    public static Intent getIntent(Context context, String name) {
        Bundle extras = new Bundle();
        extras.putString(FULL_NAME, name);
        Intent intent = new Intent(context, GalleryActivity.class);
        intent.putExtras(extras);
        return intent;
    }

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
        Bundle extras = getIntent().getExtras();
        String fullName = "";

        if (extras != null) {
            fullName = extras.getString(FULL_NAME);
        }

        if (TextUtils.isValidString(fullName)) {
            tvName.setText(fullName);
        }

        setupLoader();
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
        avLoader.setVisibility(View.VISIBLE);
        tvErrorMsg.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        avLoader.setVisibility(View.GONE);

        if (imagesAdapter.getItemCount() == 0) {
            tvErrorMsg.setVisibility(View.VISIBLE);
        }
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

                /*
                * if image type == null
                * display an image from subImages
                * */
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
    public void setCurrentPage(int page) {
        /*
        * If there is a network error,
        * then decrement the page counter
        * */
        scrollListener.setCurrentPage(page - 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    void setupLoader() {
        /*
        * Initializing margin top for
        * avloader and tvError msg
        * */

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) avLoader.getLayoutParams();
        params.topMargin = (int) (height / 3.2);
        avLoader.setLayoutParams(params);
        tvErrorMsg.setLayoutParams(params);
    }

    @OnClick(R.id.tvErrorMsg)
    public void refresh() {
        /*
        * This error will be shown only when
        * there are 0 items in the gallery
        * So load images from page 1
        * */
        presenter.getImages(1);
    }

}
