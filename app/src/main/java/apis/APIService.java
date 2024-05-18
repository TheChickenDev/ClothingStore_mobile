package apis;

import models.AuthResponseModel;
import models.GetProductResponseModel;
import models.Product;
import models.SuccessResponseModel;
import models.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @FormUrlEncoded
    @POST("user/login")
    Call<SuccessResponseModel<AuthResponseModel>> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<SuccessResponseModel<AuthResponseModel>> register(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("address") String address, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("user/forgot-password")
    Call<SuccessResponseModel<String>> getOTP(@Field("email") String email);

    @FormUrlEncoded
    @PATCH("user/reset-password")
    Call<SuccessResponseModel<String>> resetPassword(@Field("key") String key, @Field("token") String token, @Field("password") String password, @Field("confirm_password") String confirm_password);

    @GET("product/get")
    Call<SuccessResponseModel<GetProductResponseModel>> getProduct(
            @Query("limit") String limit,
            @Query("page") String page,
            @Query("sort_by") String sortBy,
            @Query("order") String order,
            @Query("price_min") String priceMin,
            @Query("price_max") String priceMax,
            @Query("rating_filter") String ratingFilter,
            @Query("name") String name,
            @Query("type") String type
    );
    @GET("user/get-by-id/{id}")
    Call<SuccessResponseModel<UserModel>> getUser(@Path("id") String userId);

    @GET("product/get-by-id/{id}")
    Call<SuccessResponseModel<Product>> getProduct(@Path("id") String productId);
    //
//    @FormUrlEncoded
    @Multipart
    @PATCH("user/update/{id}")
    Call<SuccessResponseModel<UserModel>> updateUser(
            @Path("id") String id,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part("address") RequestBody address,
            @Part MultipartBody.Part imagePart
    );

    @FormUrlEncoded
    @PATCH("user/add-to-cart/{id}")
    Call<SuccessResponseModel<UserModel>> addToCart(
            @Path("id") String userId,
            @Field("productId") String productId,
            @Field("name") String name,
            @Field("img") String img,
            @Field("size") String size,
            @Field("quantity") String quantity,
            @Field("price") String price
    );

    @FormUrlEncoded
    @PATCH("user/remove-from-cart/{id}")
    Call<SuccessResponseModel<UserModel>> removeFromCart(
            @Path("id") String userId,
            @Field("productId") String productId,
            @Field("size") String size
    );
}
