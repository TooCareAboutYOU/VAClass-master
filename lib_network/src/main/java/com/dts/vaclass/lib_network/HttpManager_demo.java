package com.dts.vaclass.lib_network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.ProcessingInstruction;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zs on 2018/2/27.
 */

public class HttpManager_demo {

    private static final String TAG = "HttpManager";

    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    private static Gson mGson;

    protected static <T> T getRetrofit(Context context,String baseUrl,Class<T> service){
        if (mRetrofit == null) {
            if (mOkHttpClient == null) {

                mGson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {

                    }
                });
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                File cacheFile=new File(context.getCacheDir(),"HttpCache");
                Cache cahce=new Cache(cacheFile,1024*1024*100); // 100Mb

                mOkHttpClient=new OkHttpClient.Builder()
                        .cookieJar(new CookieJar() {
                            @Override
                            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {

                            }

                            @Override
                            public List<Cookie> loadForRequest(HttpUrl url) {
                                return null;
                            }
                        })
                        .addInterceptor(interceptor)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(20,TimeUnit.SECONDS)
                        .readTimeout(30,TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .cache(cahce)
                        .build();
            }
            mRetrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(mGson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }


        return mRetrofit.create(service);
    }



}
