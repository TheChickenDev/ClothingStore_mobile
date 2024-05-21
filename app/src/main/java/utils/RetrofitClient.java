package utils;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;

import apis.APIService;
import classes.PreferencesManager;
import models.SuccessResponseModel;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;
    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {
            PreferencesManager preferencesManager = new PreferencesManager(context);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @NonNull
                @Override
                public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                    Request original = chain.request();

                    String accessToken = preferencesManager.getAccessToken();
                    String refreshToken = preferencesManager.getRefreshToken();

                    Request.Builder requestBuilder = original.newBuilder()
                            .header("access_token", "Bearer " + accessToken)
                            .header("refresh_token", "Bearer " + refreshToken);

                    Request request = requestBuilder.build();
                    okhttp3.Response response = chain.proceed(request);

                    if (response.code() == 401) {
                        response.close();
                        APIService apiService = retrofit.create(APIService.class);
                        Call<SuccessResponseModel<String>> refreshCall = apiService.refreshToken();
                        Response<SuccessResponseModel<String>> refreshResponse = refreshCall.execute();

                        if (refreshResponse.isSuccessful()) {
                            SuccessResponseModel<String> successResponse = refreshResponse.body();
                            if (successResponse != null) {
                                String newToken = successResponse.getData();
                                preferencesManager.saveAccessToken(newToken);

                                Request newRequest = request.newBuilder()
                                        .header("access_token", "Bearer " + newToken)
                                        .build();
                                return chain.proceed(newRequest);
                            }
                        } else {
                            throw new IOException("Refresh token failed with status: " + refreshResponse.code());
                        }
                    }

                    return response;
                }
            });

            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.171.1:5000/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
