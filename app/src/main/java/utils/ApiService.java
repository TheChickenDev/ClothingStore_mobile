package utils;

import models.Product;
import models.SuccessResponse;
import models.Users;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Part;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    public class Const {
        public static final String My_ID = "id";
        public static final String My_IMAGES = "avatar";
    }
    @GET("user/get-by-id/{id}")
    Call<SuccessResponse<Users>> getUser(@Path("id") String userId);

    @GET("product/get-by-id/{id}")
    Call<SuccessResponse<Product>> getProduct(@Path("id") String productId);
//
//    @FormUrlEncoded
    @Multipart
    @PATCH("user/update/{id}")
    Call<SuccessResponse<Users>> updateUser(@Path("id") String id, @Part("name") RequestBody name, @Part("phone") RequestBody phone, @Part("address") RequestBody address,  @Part MultipartBody.Part imagePart);

}
