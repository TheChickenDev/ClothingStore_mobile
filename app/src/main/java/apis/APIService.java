package apis;

import models.AuthResponseModel;
import models.GetProductResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("user/login")
    Call<SuccessResponseModel<AuthResponseModel>> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<SuccessResponseModel<AuthResponseModel>> register(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("address") String address, @Field("phone") String phone);

    @GET("product/get")
    Call<SuccessResponseModel<GetProductResponseModel>> getProduct();

    @FormUrlEncoded
    @POST("user/forgot-password")
    Call<SuccessResponseModel<String>> getOTP(@Field("email") String email);

    @FormUrlEncoded
    @PATCH("user/reset-password")
    Call<SuccessResponseModel<String>> resetPassword(@Field("key") String key, @Field("token") String token, @Field("password") String password, @Field("confirm_password") String confirm_password);
}
