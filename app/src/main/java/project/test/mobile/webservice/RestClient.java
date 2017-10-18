package project.test.mobile.webservice;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import project.test.mobile.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mayur on 18-10-2017.
 */

public class RestClient {
    private static final String API_BASE_URL = "https://api.imgur.com/3/";
    private static HttpLoggingInterceptor logging = getLoggerInterceptor();
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    private static APIService service = retrofit.create(APIService.class);

    public static APIService getAPIService() {
        if (service == null){
            service = retrofit.create(APIService.class);
        }

        return service;
    }

    /*
   * https://stackoverflow.com/questions/39784243/
   * cant-resolve-setlevel-on-httplogginginterceptor-retrofit2-0
   * */
    private static HttpLoggingInterceptor getLoggerInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        return logging;
    }
}
