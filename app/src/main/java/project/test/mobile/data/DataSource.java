package project.test.mobile.data;

import io.reactivex.Observable;
import project.test.mobile.models.ImgurAPIResponse;
import project.test.mobile.models.SearchResultImage;

/**
 * Created by Mayur on 18-10-2017.
 */

public interface DataSource {
    Observable<ImgurAPIResponse<SearchResultImage>> getImages(int pageNumber);
}
