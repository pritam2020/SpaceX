package com.example.info_about_country;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.pixplicity.sharp.Sharp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utilities {
    private static final String TAG = "RETROFIT TRANSACTION";
    private static OkHttpClient httpClient;
    public  static byte[] arr;

    public static void fetchSvg(Context context, String url, final ImageView target) {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 5 * 1024 * 1014))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // target.setImageDrawable(R.drawable.fallback_image);
                Log.i(TAG, "onFailure: unable to load svg");

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                Sharp.loadInputStream(stream).into(target);
                stream.close();
                Log.i(TAG, "onFailure: flag loaded");
            }
        });

    }

}
