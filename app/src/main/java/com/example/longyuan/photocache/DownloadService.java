package com.example.longyuan.photocache;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by LONGYUAN on 2017/9/28.
 */

public interface DownloadService {

    // Retrofit 2 GET request for rxjava
    @Streaming
    @GET
    Observable<Response<ResponseBody>> downloadFileByUrlRx(@Url String fileUrl);
}
