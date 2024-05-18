package com.example.clothingstore;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
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
import models.ClothModel;
import models.GetProductResponseModel;
import models.SuccessResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.RetrofitClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SearchView searchView;
    private RecyclerView rvProducts;
    private ImageButton btn_filter;
    APIService apiService;
    List<ClothModel> clothesList;
    ClothesAdapter clothesAdapter;
    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        Mapping(view);
        Search(null, null, null, null, null, null, null);
        return view;
    }

    private void Mapping(View view) {
        searchView = view.findViewById(R.id.products_search_view);
        rvProducts = view.findViewById(R.id.rvProducts);
        btn_filter = view.findViewById(R.id.products_filter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Search(null, null, null, null, null, query, null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        rvProducts.setHasFixedSize(true);
        rvProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing_6dp);
        rvProducts.addItemDecoration(new SpacesItemDecoration(spacingInPixels));
    }

    private void Search(String sortBy, String order, String priceMin, String priceMax, String ratingFilter, String query, String type) {
        apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getProduct("999", "1", sortBy, order, priceMin, priceMax, ratingFilter, query, type).enqueue(new Callback<SuccessResponseModel<GetProductResponseModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<SuccessResponseModel<GetProductResponseModel>> call, @NonNull Response<SuccessResponseModel<GetProductResponseModel>> response) {
                if (response.isSuccessful()) {
                    SuccessResponseModel<GetProductResponseModel> successResponse = response.body();
                    if (successResponse != null) {
                        clothesList = successResponse.getData().getProducts();
                        clothesAdapter = new ClothesAdapter(getContext(), clothesList);
                        rvProducts.setAdapter(clothesAdapter);
                        clothesAdapter.notifyDataSetChanged();
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