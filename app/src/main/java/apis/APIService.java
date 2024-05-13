package apis;

import java.util.List;

import models.ClothesModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {

    @GET("product/get")
    Call<List<ClothesModel>> getCategoryAll();
}
