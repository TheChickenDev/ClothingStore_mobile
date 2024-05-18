package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import adapters.ClothesAdapter;
import apis.APIService;
import models.ClothModel;
import models.GetProductResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<ClothModel> clothesList;
    RecyclerView rcClothesMale, rcClothesFemale, rcClothesUnisex, rcClothesJacket, rcClothesAccessory;
    SearchView searchView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        AnhXaMale(view);
        GetClothesMale();

        AnhXaFemale(view);
        GetClothesFemale();

        AnhXaUnisex(view);
        GetClothesUnisex();

        AnhXaJacket(view);
        GetClothesJacket();

        AnhXaAccessory(view);
        GetClothesAccessory();

        searchView = view.findViewById(R.id.home_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }
    private void AnhXaMale(View view) {
        rcClothesMale = (RecyclerView) view.findViewById(R.id.rvMale);
    }
    private void GetClothesMale() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct(10, 1, null, null, null, null, null, null, "male").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesMale.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerBestSeller = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesMale.setLayoutManager(layoutManagerBestSeller);
                    rcClothesMale.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaFemale(View view) {
        rcClothesFemale = (RecyclerView) view.findViewById(R.id.rvFemale);
    }
    private void GetClothesFemale() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct(10, 1, null, null, null, null, null, null, "female").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesFemale.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesFemale.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesFemale.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaUnisex(View view) {
        rcClothesUnisex = (RecyclerView) view.findViewById(R.id.rvUnisex);
    }
    private void GetClothesUnisex() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct(10, 1, null, null, null, null, null, null, "unisex").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesUnisex.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesUnisex.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesUnisex.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaJacket(View view) {
        rcClothesJacket= (RecyclerView) view.findViewById(R.id.rvJacket);
    }
    private void GetClothesJacket() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct(10, 1, null, null, null, null, null, null, "jacket").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesJacket.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesJacket.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesJacket.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaAccessory(View view) {
        rcClothesAccessory= (RecyclerView) view.findViewById(R.id.rvAccessory);
    }
    private void GetClothesAccessory() {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct(10, 1, null, null, null, null, null, null, "accessory").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesAccessory.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.HORIZONTAL, false);
                    rcClothesAccessory.setLayoutManager(layoutManagerNewArrivals);
                    rcClothesAccessory.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    System.out.println("Mã lỗi: " + statusCode);
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Lỗi rồi";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}