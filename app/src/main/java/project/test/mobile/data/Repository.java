package project.test.mobile.data;

import android.support.annotation.NonNull;

/**
 * Created by Mayur on 18-10-2017.
 */

public class Repository implements DataSource {

    private static Repository INSTANCE = null;
    private final DataSource remoteDataSource;
    private final DataSource localDataSource;

    private Repository(@NonNull DataSource remoteDataSource,
                       @NonNull DataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static Repository getInstance(@NonNull DataSource remoteDataSource,
                                         @NonNull DataSource localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new Repository(remoteDataSource, localDataSource);
        }

        return INSTANCE;
    }
}
