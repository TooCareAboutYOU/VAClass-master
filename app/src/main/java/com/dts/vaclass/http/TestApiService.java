package com.dts.vaclass.http;

import com.dts.vaclass.utils.manager.HttpManager;
import com.dts.vaclass.model.MusicBean;
import com.dts.vaclass.model.MoviesBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.Observer;
import rx.Subscription;


public class TestApiService extends HttpManager {

    private static final String TAG = "TestApiService";

    private static final HttpImpl service=getRetrofit("https://api.douban.com/",HttpImpl.class);
    private static final HttpImpl service2=getRetrofit("http://app.mijia.cnlive.com/",HttpImpl.class);
//  https://api.github.com/users/Guolei1130
//
    //设缓存有效期1天
    protected static final long CACHE_STALE_SEC=60*60*24;
    //查询缓存的Cache-Control设置，使用缓存
    protected static final String CACHE_CONTROL_CACHE="only-if-cached,max-stale="+CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，不使用缓存
    protected static final String CACHE_CONTROL_NETWORK="max-age=0";


    private interface HttpImpl {

        @GET("v2/movie/top250")
        Call<MoviesBean> getMovieHttpData(@Query("start") int start, @Query("count") int count);

        @GET("api_v1/columnprograms.do")
        Call<MusicBean> getMusicHttpData(@Query("cid") String cid);

        @GET("api_v1/columnprograms.do")
        Observable<MusicBean> getMusicHttpData2(@Query("cid") String cid);

    }

    public static Call<MoviesBean> GetMovieData(int start,int count){
        return service.getMovieHttpData(start, count);
    }

    public static Call<MusicBean> GetMusicData(String cid){
        return service2.getMusicHttpData(cid);
    }

    public static Subscription GetMusicData2(String cid, Observer<MusicBean> observer){
        return setSubscribe(service2.getMusicHttpData2(cid),observer);
    }

}
