package project.test.mobile.data;

import io.reactivex.Observable;
import project.test.mobile.models.ImgurAPIResponse;
import project.test.mobile.models.SearchResultImage;
import project.test.mobile.webservice.APIService;
import project.test.mobile.webservice.RestClient;

/**
 * Created by Mayur on 18-10-2017.
 */

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE;
    APIService service = RestClient.getAPIService();

    // prevent direct instantiation
    private RemoteDataSource() {
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }

        return INSTANCE;
    }

    @Override
    public Observable<ImgurAPIResponse<SearchResultImage>> getImages() {
        return service.getImages();
    }
}
