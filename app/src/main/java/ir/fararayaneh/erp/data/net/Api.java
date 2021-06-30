package ir.fararayaneh.erp.data.net;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;

import java.util.concurrent.TimeUnit;

import ir.fararayaneh.erp.App;
import ir.fararayaneh.erp.commons.CommonUrls;
import ir.fararayaneh.erp.data.data_providers.shared_preference.SharedPreferenceProvider;
import ir.fararayaneh.erp.utils.IPHandler;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    private static final short TIME_OUT_CONNECTION = 80;
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String HEADER_VALUE = "no-cache";


    public static <S> S createService(Class<S> serviceClass) {
        return retrofitBuilder().build().create(serviceClass);
    }

    //todo correct base url
    private static Retrofit.Builder retrofitBuilder() {
        return new Retrofit.Builder()
                .baseUrl(IPHandler.creatorForRest(CommonUrls.HTTP_BASE_URL,SharedPreferenceProvider.getServerConfigs(App.getAppContext()),CommonUrls.MIDDLE_BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient());
    }


    private static OkHttpClient httpClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .addNetworkInterceptor(chain -> {
                    Response originalResponse = chain.proceed(makeRequest(chain));
                    return originalResponse.newBuilder()
                            .header(HEADER_CACHE_CONTROL, HEADER_VALUE)
                            .build();
                })
                .build();

    }

    private static Request makeRequest(Interceptor.Chain chain) {
        Request request = chain.request();
        request = request.newBuilder()
                .header(HEADER_CACHE_CONTROL, HEADER_VALUE)
                .build();
        return request;
    }



    public static void initAndroidNetwork(Context context){
        AndroidNetworking.initialize(context,httpClient());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
    }

}
