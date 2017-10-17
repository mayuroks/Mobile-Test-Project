package project.test.mobile.data;

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
}
