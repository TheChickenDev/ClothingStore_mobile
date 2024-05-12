package com.example.clothingstore.service;

import com.example.clothingstore.model.Product;
import com.example.clothingstore.model.SuccessResponse;
import com.example.clothingstore.model.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ApiService {
    @GET("user/get-by-id/{id}")
    Call<SuccessResponse<Users>> getUser(@Path("id") String userId);

    @GET("product/get-by-id/{id}")
    Call<SuccessResponse<Product>> getProduct(@Path("id") String productId);
    @PATCH("user/update/{id}")
    Call<SuccessResponse<Users>> updateUser(@Path("id") String userId ,@Body Users updateUser);

}
