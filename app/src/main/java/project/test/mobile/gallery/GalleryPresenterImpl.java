package project.test.mobile.gallery;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import project.test.mobile.data.Repository;
import project.test.mobile.models.ImgurAPIResponse;
import project.test.mobile.models.SearchResultImage;
import project.test.mobile.utils.Injection;
import project.test.mobile.utils.schedulers.BaseSchedulerProvider;

/**
 * Created by Mayur on 18-10-2017.
 */

public class GalleryPresenterImpl implements GalleryActivityContract.GalleryPresenter {

    GalleryActivityContract.GalleryView view;

    BaseSchedulerProvider schedulerProvider;

    Repository repository;

    public GalleryPresenterImpl(@NonNull GalleryActivityContract.GalleryView view,
                                @NonNull BaseSchedulerProvider schedulerProvider,
                                @NonNull Repository repository) {
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.repository = repository;
        view.setPresenter(this);
        view.initView();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getImages(int pageNumber) {
        Logger.i("presenter getting Images");
        repository.getImages(pageNumber)
                .subscribeOn(Injection.provideSchedulerProvider().io())
                .observeOn(Injection.provideSchedulerProvider().ui())
                .subscribe(new Observer<ImgurAPIResponse<SearchResultImage>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ImgurAPIResponse<SearchResultImage> APIResponse) {
                        Logger.i("Successfully loaded images");
                        if (view != null) {
                            ArrayList<SearchResultImage> images = APIResponse.getData();
                            view.showImages(images);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Logger.i("error in loading images");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
