package com.example.longyuan.photocache.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.example.longyuan.photocache.DownloadService;

import java.io.InputStream;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by LONGYUAN on 2017/9/28.
 */

public class NetCacheUtils {

    private LocalCacheUtils mLocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;


    DownloadService downloadService;


    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils)
    {
        mLocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    /**
     * 从网络下载图片
     * @param ivPic 显示图片的imageview
     * @param url   下载图片的网络地址
     */
    public void getBitmapFromNet(ImageView ivPic, String url) {

        downloadService = createService(DownloadService.class, "https://developer.android.com/design/media/");

        downloadService.downloadFileByUrlRx("principles_real_objects.png")
                .flatMap(res -> Observable.just(BitmapFactory
                        .decodeStream((InputStream) res.body().byteStream())))
                // .flatMap(processResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.subscribe(bmp -> ivPic.setImageBitmap(bmp));
                 .subscribe(bmp -> handleImage(bmp,ivPic));
    }

    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit.create(serviceClass);
    }

    private void handleImage(Bitmap bitmap,ImageView imageView){

        imageView.setImageBitmap(bitmap);

        //mLocalCacheUtils.setBitmapToLocal("test",bitmap);

        mMemoryCacheUtils.setBitmapToMemory("test",bitmap);

    }
}
