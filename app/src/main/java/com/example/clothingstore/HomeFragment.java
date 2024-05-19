package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import adapters.ClothesAdapter;
import apis.APIService;
import classes.PreferencesManager;
import classes.SpacesItemDecoration;
import models.ProductModel;
import models.GetProductResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class HomeFragment extends Fragment {
    ClothesAdapter clothesAdapter;
    APIService apiService;
    List<ProductModel> clothesList;
    RecyclerView rcClothesMale, rcClothesFemale, rcClothesUnisex, rcClothesJacket, rcClothesAccessory;
    SearchView searchView;
    ImageButton btn_cart;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                if (query.isEmpty()) return false;

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                ProductsFragment productsFragment = new ProductsFragment();
                Bundle args = new Bundle();
                args.putString("query", query);
                productsFragment.setArguments(args);

                transaction.replace(R.id.main_frame_layout, productsFragment);
                transaction.addToBackStack(null);
                transaction.commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        btn_cart = view.findViewById(R.id.home_btn_cart);
        btn_cart.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CartActivity.class);
            startActivity(intent);
        });
        return view;
    }
    private void AnhXaMale(View view) {
        rcClothesMale = (RecyclerView) view.findViewById(R.id.rvMale);

        rcClothesMale.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rcClothesMale.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rcClothesMale.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }
    private void GetClothesMale() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext());
        apiService = RetrofitClient.getRetrofit(getContext()).create(APIService.class);
        apiService.getProduct("10", "1", null, null, null, null, null, null, "male").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesMale.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Toast.makeText(getContext(), "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Error!";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaFemale(View view) {
        rcClothesFemale = (RecyclerView) view.findViewById(R.id.rvFemale);

        rcClothesFemale.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rcClothesFemale.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rcClothesFemale.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }
    private void GetClothesFemale() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext());
        apiService = RetrofitClient.getRetrofit(getContext()).create(APIService.class);
        apiService.getProduct("10", "1", null, null, null, null, null, null, "female").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesFemale.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Toast.makeText(getContext(), "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Error!";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaUnisex(View view) {
        rcClothesUnisex = (RecyclerView) view.findViewById(R.id.rvUnisex);

        rcClothesUnisex.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rcClothesUnisex.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rcClothesUnisex.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }
    private void GetClothesUnisex() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext());
        apiService = RetrofitClient.getRetrofit(getContext()).create(APIService.class);
        apiService.getProduct("10", "1", null, null, null, null, null, null, "unisex").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesUnisex.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Toast.makeText(getContext(), "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Error!";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaJacket(View view) {
        rcClothesJacket= (RecyclerView) view.findViewById(R.id.rvJacket);

        rcClothesJacket.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rcClothesJacket.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rcClothesJacket.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }
    private void GetClothesJacket() {
        PreferencesManager preferencesManager = new PreferencesManager(getContext());
        apiService = RetrofitClient.getRetrofit(getContext()).create(APIService.class);
        apiService.getProduct("10", "1", null, null, null, null, null, null, "jacket").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesJacket.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Toast.makeText(getContext(), "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Error!";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXaAccessory(View view) {
        rcClothesAccessory= (RecyclerView) view.findViewById(R.id.rvAccessory);

        rcClothesAccessory.setHasFixedSize(true);
        LinearLayoutManager layoutManagerNewArrivals = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        rcClothesAccessory.setLayoutManager(layoutManagerNewArrivals);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rcClothesAccessory.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }
    private void GetClothesAccessory() {
        apiService = RetrofitClient.getRetrofit(getContext()).create(APIService.class);
        apiService.getProduct("10", "1", null, null, null, null, null, null, "accessory").enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    clothesList = successResponse.getData().getProducts();
                    clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                    rcClothesAccessory.setAdapter(clothesAdapter);
                    clothesAdapter.notifyDataSetChanged();
                } else {
                    int statusCode = response.code();
                    Toast.makeText(getContext(), "Error! Status code: " + statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Throwable t) {
                String message = t.getMessage() != null ? t.getMessage() : "Error!";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}