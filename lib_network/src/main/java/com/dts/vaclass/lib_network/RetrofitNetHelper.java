package com.dts.vaclass.lib_network;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by zs on 2018/2/6.
 */

public class RetrofitNetHelper {

    private static final String TAG = "RetrofitNetHelper";

    public static final String BASE_URL = "Config";

    private Context mContext;


    private static volatile RetrofitNetHelper instance=null;

    private Retrofit mRetrofit;

    public RetrofitNetHelper(){
        Gson mGson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("zgx", "OkHttp====message " + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile=new File(mContext.getCacheDir(),"HttpCache");
        Cache cahce=new Cache(cacheFile,1024*1024*100); // 100Mb

        OkHttpClient mOkHttpClient =new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new HttpBaseParamsLoggingInterceptor())
                .addInterceptor(new HttpURLInterceptor())
                .cache(cahce)
                .build();

        mRetrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .client(mOkHttpClient)
                .build();
    }

    class HttpBaseParamsLoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            Request.Builder requestBuilder = request.newBuilder();
//            RequestBody formBody = new FormBody.Builder()
//                    .add("userId", "10000")
//                    .add("sessionToken", "E34343RDFDRGRT43RFERGFRE")
//                    .add("q_version", "1.1")
//                    .add("device_id", "android-344365")
//                    .add("device_os", "android")
//                    .add("device_osversion", "6.0")
//                    .add("req_timestamp", System.currentTimeMillis() + "")
//                    .add("app_name", "forums")
//                    .add("sign", "md5")
//                    .build();
//            String postBodyString = Utils.bodyToString(request.body());
//            postBodyString += ((postBodyString.length() > 0) ? "&" : "") +  Utils.bodyToString(formBody);
//            request = requestBuilder
//                    .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
//                            postBodyString))
//                    .build();
            return chain.proceed(request);
        }
    }

    class HttpURLInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request=chain.request();
            Response response=chain.proceed(request);
            String requestURL=response.request().url().uri().getPath();
            if (!TextUtils.isEmpty(requestURL)) {
                if (requestURL.contains("LoginDtaServlet")) {
                    if (Looper.myLooper() == null) {
                        Looper.prepare();
                    }
                    createObservable("现在请求的是登录接口");
                }
            }
            return response;
        }
    }

    private void createObservable(String msg){
        Observable
            .just(msg)
            .map(new Func1<String, String>() {
                @Override
                public String call(String s) {
                    return s;
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {

                }
            });
    }

    public static synchronized RetrofitNetHelper getInstance(){
        if(instance==null){
            synchronized(RetrofitNetHelper.class){
                if(instance==null){
                    instance=new RetrofitNetHelper();
                }
            }
        }
        return instance;
    }


    //定义接口
//    public interface ILoginService {
//        @GET("LoginDataServlet")
//        @Headers("Cache-Control: public, max-age=30")
//        Call<BaseResp<T>> userLogin(@Query("username") String username, @Query("password") String password);
//    }


}
