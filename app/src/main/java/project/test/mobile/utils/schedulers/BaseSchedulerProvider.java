package project.test.mobile.utils.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/**
 * Created by Mayur on 18-10-2017.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

}
