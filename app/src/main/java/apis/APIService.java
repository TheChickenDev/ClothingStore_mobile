package apis;

import java.util.List;

import models.ClothModel;
import models.GetProductResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("product/get")
    Call<SuccessResponseModel<GetProductResponseModel>> getProduct();
}
