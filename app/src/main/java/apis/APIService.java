package apis;

import java.util.List;

import models.Clothes;
import models.SuccessResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("product/get")
    Call<List<Clothes>> getProduct();
}
