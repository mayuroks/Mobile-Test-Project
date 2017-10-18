package project.test.mobile.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
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

    private ImagesAdapter imagesAdapter;
    private GridLayoutManager layoutManager;
    private GalleryActivityContract.GalleryPresenter presenter;
    private EndlessRecyclerViewScrollListener scrollListener;

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
        setupImages();
        presenter.getImages(1);
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
        rvImages.setNestedScrollingEnabled(false);
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
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

        imagesAdapter.addItems(cleanImages);
        imagesAdapter.notifyDataSetChanged();
    }
}
