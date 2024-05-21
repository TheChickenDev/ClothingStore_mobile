package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import adapters.ClothesAdapter;
import apis.APIService;
import classes.SpacesItemDecoration;
import models.ProductModel;
import models.GetProductResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

public class ProductsFragment extends Fragment {
    private String arrangement;
    private String type;
    private String price;
    private String rating;
    private String query;

    private RecyclerView rvProducts;
    SearchView searchView;
    ImageButton btn_filter;
    APIService apiService;
    List<ProductModel> clothesList;
    ClothesAdapter clothesAdapter;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrangement = "";
        type = "";
        price = "";
        rating = "";
        query = "";
        Bundle args = getArguments();
        if (args != null) {
            arrangement = args.getString("arrangement");
            type = args.getString("type");
            price = args.getString("price");
            rating = args.getString("rating");
            query = args.getString("query");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        Mapping(view);
        HandleSearchRequest();
        return view;
    }

    private void Mapping(View view) {
        searchView = view.findViewById(R.id.products_search_view);
        rvProducts = view.findViewById(R.id.rvProducts);
        btn_filter = view.findViewById(R.id.products_filter);

        if (query != null) {
            searchView.setQuery(query, false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query = q;
                HandleSearchRequest();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btn_filter.setOnClickListener(v -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment != null) {
                FragmentTransaction transaction = parentFragment.getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.shop_frame_layout, new FilterFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rvProducts.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    private void HandleSearchRequest() {
        String sortBy = null;
        String order = null;
        String priceMin = null;
        String priceMax = null;
        String ratingFilter = null;
        if (!(arrangement == null))
            switch (arrangement) {
                case "New products":
                    sortBy = "updatedAt";
                    break;
                case "Popular products":
                    sortBy = "view";
                    break;
                case "Best seller":
                    sortBy = "sold";
                    break;
                case "Price asc":
                    sortBy = "price";
                    order = "asc";
                    break;
                case "Price desc":
                    sortBy = "price";
                    order = "desc";
                    break;
            }
        if (!(price == null))
            switch (price) {
                case "1.000đ - 50.000đ":
                    priceMin = "1000";
                    priceMax = "50000";
                    break;
                case "51.000đ - 100.000đ":
                    priceMin = "51000";
                    priceMax = "100000";
                    break;
                case "101.000đ - 150.000đ":
                    priceMin = "101000";
                    priceMax = "150000";
                    break;
                case "151.000đ - 200.000đ":
                    priceMin = "151000";
                    priceMax = "200000";
                    break;
                case "> 200.000đ":
                    priceMin = "200000";
                    break;
            }
        if (!(rating == null))
            switch (rating) {
                case "★ ☆ ☆ ☆ ☆":
                    ratingFilter = "1";
                    break;
                case "★ ★ ☆ ☆ ☆":
                    ratingFilter = "2";
                    break;
                case "★ ★ ★ ☆ ☆":
                    ratingFilter = "3";
                    break;
                case "★ ★ ★ ★ ☆":
                    ratingFilter = "4";
                    break;
                case "★ ★ ★ ★ ★":
                    ratingFilter = "5";
                    break;
            }
        Search(sortBy, order, priceMin, priceMax, ratingFilter, query, type == null ? null : type.toLowerCase());
    }

    private void Search(String sortBy, String order, String priceMin, String priceMax, String ratingFilter, String query, String type) {
        apiService = RetrofitClient.getRetrofit(getContext()).create(APIService.class);
        apiService.getProduct("999", "1", sortBy, order, priceMin, priceMax, ratingFilter, query, type).enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    if (successResponse != null) {
                        clothesList = successResponse.getData().getProducts();
                        if (clothesList.isEmpty()) {
                            Toast.makeText(getContext(), "Không tìm thấy sản phẩm phù hợp!", Toast.LENGTH_SHORT).show();
                        } else {
                            clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                            rvProducts.setAdapter(clothesAdapter);
                            clothesAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    int statusCode = response.code();
                    Toast.makeText(getActivity(), "Lỗi rồi kìa! Mã lỗi: " + statusCode, Toast.LENGTH_SHORT).show();
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