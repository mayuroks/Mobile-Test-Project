package project.test.mobile.data;

import io.reactivex.Observable;
import project.test.mobile.models.ImgurAPIResponse;
import project.test.mobile.models.SearchResultImage;

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

    @Override
    public Observable<ImgurAPIResponse<SearchResultImage>> getImages(int pageNumber) {
        return null;
    }
}
