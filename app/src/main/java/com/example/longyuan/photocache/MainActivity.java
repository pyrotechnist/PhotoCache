package com.example.longyuan.photocache;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.longyuan.photocache.Utils.LocalCacheUtils;
import com.example.longyuan.photocache.Utils.MyBitmapUtils;
import com.example.longyuan.photocache.Utils.NetCacheUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;


public class MainActivity extends Activity {


    DownloadService downloadService;

    private ImageView mImageView;

    private Button mButtonRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.pic);

        mButtonRefresh = (Button) findViewById(R.id.refresh);

        MyBitmapUtils myBitmapUtils = new MyBitmapUtils();

        mButtonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // solution 1 :  net +  memory + local file
                //myBitmapUtils.disPlay(mImageView,"test");

                // solution 2 : glide
                Glide.with(getApplicationContext())
                        //.load("https://developer.android.com/design/media/principles_real_objects.png")
                        .load("https://media.giphy.com/media/gw3IWyGkC0rsazTi/giphy.gif")
                        //.asBitmap()    // gif as bitmap , no playing
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(android.R.drawable.ic_input_delete)
                        .skipMemoryCache(false)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .crossFade(5000)
                        .into(mImageView);

            }
        });








        /*downloadService = createService(DownloadService.class, "https://developer.android.com/design/media/");

        downloadService.downloadFileByUrlRx("principles_real_objects.png")
                .flatMap(res -> Observable.just(BitmapFactory
                    .decodeStream((InputStream) res.body().byteStream())))
               // .flatMap(processResponse())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bmp -> mImageView.setImageBitmap(bmp));
*/

    }

    /*private Func1<Response<ResponseBody>, Observable<Bitmap>> processResponse() {
        return new Func1<Response<ResponseBody>, Observable<Bitmap>>() {
            @Override
            public Observable<Bitmap> call(Response<ResponseBody> responseBodyResponse) {

                Bitmap bitmap = BitmapFactory
                        .decodeStream((InputStream) responseBodyResponse.body().byteStream());
                return Observable.just(bitmap);

            }
        };
    }


    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        return retrofit.create(serviceClass);
    }

    private Observer<Bitmap> handleResult() {
        return new Observer<Bitmap>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Log.d(TAG, "Error " + e.getMessage());
            }

            @Override
            public void onNext(Bitmap file) {
                // Log.d(TAG, "File downloaded to " + file.getAbsolutePath());
                mImageView.setImageBitmap(file);
            }
        };
    }*/
}
