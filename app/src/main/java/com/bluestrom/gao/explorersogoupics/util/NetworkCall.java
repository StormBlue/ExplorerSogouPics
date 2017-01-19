package com.bluestrom.gao.explorersogoupics.util;

import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Gao-Krund on 2017/1/18.
 */
public class NetworkCall {
    private static OkHttpClient httpInstance = new OkHttpClient();

    public static OkHttpClient getHttpInstance() {
        return httpInstance;
    }

    private NetworkCall() {
    }

    public static void asynNetworkGet(String httpUrl, @Nullable Map<String, String> headers, @Nullable Map<String, String> params, Callback callback) {
        if (params != null && !params.isEmpty()) {
            httpUrl += "?";
            for (Object o : params.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                try {
                    httpUrl += entry.getKey() + "=" + URLEncoder.encode((String) entry.getValue(), "UTF-8") + "&";
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        Request.Builder requestBuilder = new Request.Builder().url(httpUrl);
        if (headers != null && !headers.isEmpty()) {
            requestBuilder.headers(Headers.of(headers));
        }
        getHttpInstance().newCall(requestBuilder.build()).enqueue(callback);
    }
}
