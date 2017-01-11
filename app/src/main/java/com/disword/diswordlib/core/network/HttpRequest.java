package com.disword.diswordlib.core.network;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by disword on 17/1/10.
 */

class HttpRequest {
    private static final OkHttpClient mOkHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static void setTimeout(){

    }

    public static String get(String url) throws IOException{
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }



    public static String postJSON(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
