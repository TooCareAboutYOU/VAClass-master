package com.dts.vaclass.utils.manager;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zs on 2018/2/27.
 */

public class HttpManager {


    private static final long DEFAULT_TIME_OUT=10;  //超时时间
    private static final long DEFAULT_WRITE_TIME_OUT=10;  //超时时间
    private static final long DEFAULT_READ_TIME_OUT=10;  //超时时间




    protected static <T>T getRetrofit(String baseUrl,Class<T> service) {

        Gson mGson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//
//            }
//        });
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder mOkHttpClient=new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIME_OUT,TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIME_OUT,TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_READ_TIME_OUT,TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true);


//                .addInterceptor(interceptor);

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return mRetrofit.create(service);
    }

    protected static <T> Subscription setSubscribe(Observable<T> observable,Observer<T> observer){
        Subscription subscription=observable
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return subscription;
    }
}
