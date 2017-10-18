package project.test.mobile.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.BindView;
import project.test.mobile.BaseActivity;
import project.test.mobile.R;
import project.test.mobile.models.SearchResultImage;
import project.test.mobile.utils.Injection;
import project.test.mobile.utils.TextUtils;

/**
 * Created by Mayur on 17-10-2017.
 */

public class GalleryActivity extends BaseActivity
        implements GalleryActivityContract.GalleryView {

    @BindView(R.id.rvImages)
    RecyclerView rvImages;

    ImagesAdapter imagesAdapter;
    GridLayoutManager layoutManager;
    StaggeredGridLayoutManager layoutManager1;
    GalleryActivityContract.GalleryPresenter presenter;

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
        presenter.getImages();
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

    @Override
    public void showImages(ArrayList<SearchResultImage> images) {
        Logger.i("showing images");
        ArrayList<SearchResultImage> cleanImages = new ArrayList<>();

        for (SearchResultImage image : images) {
            if (image.getType() == null) {
                ArrayList<SearchResultImage> subImages = image.getImages();

                if (subImages != null && subImages.size() > 0) {
                    SearchResultImage image1 = subImages.get(0);
                    cleanImages.add(image1);
                }
            }
        }

        imagesAdapter = new ImagesAdapter(this, cleanImages);
        layoutManager = new GridLayoutManager(this, 2);
//        layoutManager1 = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
//        layoutManager1.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvImages.setAdapter(imagesAdapter);
        rvImages.setLayoutManager(layoutManager);
        rvImages.setHasFixedSize(true);
        rvImages.setNestedScrollingEnabled(false);
    }
}
