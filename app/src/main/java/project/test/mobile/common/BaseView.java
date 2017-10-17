package project.test.mobile.common;

/**
 * Created by Mayur on 18-10-2017.
 */

public interface BaseView<T> {

    void initView();

    void setPresenter(T presenter);

    void showProgress();

    void hideProgress();

}
