package project.test.mobile.gallery;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import project.test.mobile.data.Repository;
import project.test.mobile.models.SearchResultImage;
import project.test.mobile.utils.Injection;
import project.test.mobile.utils.schedulers.BaseSchedulerProvider;

/**
 * Created by Mayur on 18-10-2017.
 */

public class GalleryPresenterImpl implements GalleryActivityContract.GalleryPresenter {

    private static final int API_DELAY = 2;

    GalleryActivityContract.GalleryView view;

    BaseSchedulerProvider schedulerProvider;

    Repository repository;

    Context context;

    public GalleryPresenterImpl(@NonNull Context context,
                                @NonNull GalleryActivityContract.GalleryView view,
                                @NonNull BaseSchedulerProvider schedulerProvider,
                                @NonNull Repository repository) {
        this.context = context;
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
    public void getImages(final int pageNumber) {
        Logger.i("presenter getting Images");
        view.showProgress();

        Observable observable = Observable.just(1);

        final ArrayList<SearchResultImage> images = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            SearchResultImage image = new SearchResultImage();
            images.add(image);
        }

        observable.delay(API_DELAY, TimeUnit.SECONDS)
                .subscribeOn(Injection.provideSchedulerProvider().io())
                .observeOn(Injection.provideSchedulerProvider().ui())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        if (isOnline()) {
                            view.hideProgress();
                            view.showImages(images);
                        } else {
                            view.hideProgress();
                            view.setCurrentPage(pageNumber);
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

    private boolean isOnline() {
        boolean isConnected = false;
        ConnectivityManager cm =(ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
