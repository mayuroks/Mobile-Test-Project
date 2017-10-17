package project.test.mobile.utils;

import android.content.Context;

import project.test.mobile.data.LocalDataSource;
import project.test.mobile.data.RemoteDataSource;
import project.test.mobile.data.Repository;
import project.test.mobile.utils.schedulers.BaseSchedulerProvider;
import project.test.mobile.utils.schedulers.SchedulerProvider;

/**
 * Created by Mayur on 18-10-2017.
 */

public class Injection {
    public static Repository providesRepository(Context context) {
        return Repository.getInstance(RemoteDataSource.getInstance(),
                LocalDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
