package project.test.mobile.webservice;

import io.reactivex.Observable;
import project.test.mobile.models.ImgurAPIResponse;
import project.test.mobile.models.SearchResultImage;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Mayur on 18-10-2017.
 */

// FIXME add a refresh token call
public interface APIService {

    @Headers("authorization: Client-ID db854f1ee022a6b")
    @GET("gallery/search/top/top/{page_number}?q=cats&q_size_px=small")
    Observable<ImgurAPIResponse<SearchResultImage>> getImages(@Path("page_number") int pageNumber);

}
