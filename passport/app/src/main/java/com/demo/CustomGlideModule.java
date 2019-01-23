package com.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.fox.one.support.framework.network.HttpEngine;

import java.io.InputStream;

@GlideModule
public class CustomGlideModule extends AppGlideModule {

    private HttpEngine mHttpEngine;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        builder.setDefaultRequestOptions(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .optionalCenterCrop()
                .format(DecodeFormat.PREFER_ARGB_8888));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //TODO: need to refactor restful api request
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory(httpEngine().getOkHttpClient());
        glide.getRegistry().replace(GlideUrl.class, InputStream.class, factory);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    private HttpEngine httpEngine() {
        if (mHttpEngine == null) {
            mHttpEngine = new HttpEngine();

//            mHttpEngine.getOkHttpClient().interceptors().add(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request();
//                    Request.Builder newRequestBuilder = request.newBuilder();
//
//                    newRequestBuilder.header(HttpEngine.HEADER_KEY_CONNECTION, HttpEngine.HEADER_VALUE_CONNECTION_CLOSE);
//
//                    return chain.proceed(newRequestBuilder.build());
//                }
//            });
        }

        return mHttpEngine;
    }
}
