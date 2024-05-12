package apis;

import models.LoginResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @FormUrlEncoded
    @POST("user/login")
    Call<SuccessResponseModel<LoginResponseModel>> login(@Field("email") String email, @Field("password") String password);
}
