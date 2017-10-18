package project.test.mobile.gallery;

import java.util.ArrayList;

import project.test.mobile.common.BasePresenter;
import project.test.mobile.common.BaseView;
import project.test.mobile.models.SearchResultImage;

/**
 * Created by Mayur on 18-10-2017.
 */

public interface GalleryActivityContract {

    interface GalleryView extends BaseView<GalleryPresenter> {
        void showImages(ArrayList<SearchResultImage> images);
    }

    interface GalleryPresenter extends BasePresenter {
        void getImages();
    }

}
