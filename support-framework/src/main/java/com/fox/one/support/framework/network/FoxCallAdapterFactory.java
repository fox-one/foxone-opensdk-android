package com.fox.one.support.framework.network;

import com.fox.one.support.common.utils.JsonUtils;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.concurrent.Executor;


/**
 * class description here
 *
 * @author xiaoming1109@gmail.com
 * @version 1.0.0
 * @since 2018-09-05
 */
public class FoxCallAdapterFactory extends CallAdapter.Factory {
    final Executor callbackExecutor;

    public FoxCallAdapterFactory(Executor callbackExecutor) {
        this.callbackExecutor = callbackExecutor;
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != FoxCall.class) {
            return null;
        }

        try {
            final Type responseType = Utils.getCallResponseType(returnType);

            return new CallAdapter<Object, FoxCall<?>>() {
                @Override
                public Type responseType() {
                    return responseType;
                }

                @Override
                public FoxCall<Object> adapt(Call<Object> call) {
                    return new ExecutorCallbackCall<>(callbackExecutor, call);
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static final class ExecutorCallbackCall<T> implements FoxCall<T> {
        final Executor callbackExecutor;
        final Call<T> delegate;

        ExecutorCallbackCall(Executor callbackExecutor, Call<T> delegate) {
            this.callbackExecutor = callbackExecutor;
            this.delegate = delegate;
        }

        @Override
        public void enqueue(final Callback<T> callback) {
            if (callback == null) {
                return;
            }
            delegate.enqueue(new Callback<T>() {
                @Override
                public void onResponse(final Call<T> call, final Response<T> response) {
                    callbackExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if (delegate.isCanceled()) {
                                // Emulate OkHttp's behavior of throwing/delivering an IOException on cancellation.
                                callback.onFailure(ExecutorCallbackCall.this, new IOException("Canceled"));
                            } else {
                                if (response.isSuccessful() && response.body() != null) {
                                    //response.code() >= 200 && response.code() < 300

                                    if (response.body() instanceof BaseResponse) {
                                        if (((BaseResponse) response.body()).isSuccessful()) {
                                            callback.onResponse(call, response);
                                        } else {
                                            Response rsp = Response.error(HttpURLConnection.HTTP_INTERNAL_ERROR, ResponseBody.create(MediaType.parse("application/json"), JsonUtils.optToJson(response.body())));
                                            callback.onFailure(call, new HttpException(rsp));
                                        }
                                    } else {
                                        callback.onResponse(call, response);
                                    }
                                } else {
                                    callback.onFailure(ExecutorCallbackCall.this, new HttpException(response));
                                }
                            }
                        }
                    });
                }

                @Override
                public void onFailure(final Call<T> call, final Throwable t) {
                    callbackExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(ExecutorCallbackCall.this, t);
                        }
                    });
                }
            });
        }

        @Override
        public boolean isExecuted() {
            return delegate.isExecuted();
        }

        @Override
        public Response<T> execute() throws IOException {
            return delegate.execute();
        }

        @Override
        public void cancel() {
            delegate.cancel();
        }

        @Override
        public boolean isCanceled() {
            return delegate.isCanceled();
        }

        @SuppressWarnings("CloneDoesntCallSuperClone") // Performing deep clone.
        @Override
        public Call<T> clone() {
            return new ExecutorCallbackCall<>(callbackExecutor, delegate.clone());
        }

        @Override
        public Request request() {
            return delegate.request();
        }
    }
}
