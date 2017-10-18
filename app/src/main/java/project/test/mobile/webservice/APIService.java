package project.test.mobile.webservice;

import io.reactivex.Observable;
import project.test.mobile.models.ImgurAPIResponse;
import project.test.mobile.models.SearchResultImage;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Mayur on 18-10-2017.
 */

// FIXME add a refresh token call
public interface APIService {

    @Headers("authorization: Client-ID db854f1ee022a6b")
    @GET("3/gallery/search/top/top/1?q=cats&q_size_px=small")
    Observable<ImgurAPIResponse<SearchResultImage>> getImages();

}
