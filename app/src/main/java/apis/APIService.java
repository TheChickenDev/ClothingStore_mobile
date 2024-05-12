package apis;

import java.util.List;

import models.ClothesModel;
import models.LoginResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("product/get")
    Call<List<ClothesModel>> getClothesAll();
    @FormUrlEncoded
    @POST("user/login")
    Call<SuccessResponseModel<LoginResponseModel>> login(@Field("email") String email, @Field("password") String password);
}
