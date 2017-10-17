package project.test.mobile.data;

/**
 * Created by Mayur on 18-10-2017.
 */

public class LocalDataSource implements DataSource {

    private static LocalDataSource INSTANCE;

    // prevent direct instantiation
    private LocalDataSource() {
    }

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocalDataSource();
        }

        return INSTANCE;
    }

}
