package com.example.project_nhom_1.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom_1.R;
import com.example.project_nhom_1.adapter.ProductAdapterSearch;
import com.example.project_nhom_1.api.Api;
import com.example.project_nhom_1.api.ApiPet;
import com.example.project_nhom_1.model.Pet;
import com.example.project_nhom_1.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapterSearch productAdapterSearch;
    private List<Object> productList;
    private List<Object> originalProductList = new ArrayList<>();
    private FrameLayout searchFragmentContainer;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        originalProductList = new ArrayList<>();
        productList = new ArrayList<>();
        productAdapterSearch = new ProductAdapterSearch(productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(productAdapterSearch);

        loadProducts();
        loadPets();

        EditText searchEditText = view.findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed while text is changing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterProducts(editable.toString());
            }
        });

        Button searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = searchEditText.getText().toString();
                filterProducts(productName);
            }
        });

        return view;
    }

    private void loadProducts() {
        Api.api.getListProduct().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                handleProductResponse(response);
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                handleFailure(t);
            }
        });
    }

    private void loadPets() {
        ApiPet.apiPet.getListPet().enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                handlePetResponse(response);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                handleFailure(t);
            }
        });
    }

    private void filterProducts(String productName) {
        productList.clear();
        List<Object> filteredList = new ArrayList<>();

        if (TextUtils.isEmpty(productName)) {
            // If the product name is empty, return the original list
            filteredList.addAll(originalProductList);
        } else {
            // Perform filtering based on the product name
            for (Object product : originalProductList) {
                if (product instanceof Product) {
                    if (((Product) product).getName().toLowerCase().contains(productName.toLowerCase())) {
                        filteredList.add(product);
                    }
                } else if (product instanceof Pet) {
                    if (((Pet) product).getName().toLowerCase().contains(productName.toLowerCase())) {
                        filteredList.add(product);
                    }
                }
            }
        }

        productAdapterSearch.updateData(filteredList);
    }

    private void handleProductResponse(Response<List<Product>> response) {
        if (response.isSuccessful()) {
            List<Product> products = response.body();

            if (products != null && !products.isEmpty()) {
                originalProductList.addAll(products);
                productList.addAll(products);
                productAdapterSearch.notifyDataSetChanged();
            } else {
                // Show a message if there is no data
                Toast.makeText(requireContext(), "No products available", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a message if the request was unsuccessful
            Toast.makeText(requireContext(), "Error getting data from Product", Toast.LENGTH_SHORT).show();
        }
    }

    private void handlePetResponse(Response<List<Pet>> response) {
        if (response.isSuccessful()) {
            List<Pet> pets = response.body();

            if (pets != null && !pets.isEmpty()) {
                originalProductList.addAll(pets);
                productList.addAll(pets);
                productAdapterSearch.notifyDataSetChanged();
            } else {
                // Show a message if there is no data
                Toast.makeText(requireContext(), "No products available from Pet", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Show a message if the request was unsuccessful
            Toast.makeText(requireContext(), "Error getting data from Pet", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleFailure(Throwable t) {
        // Show a message for failure
        Toast.makeText(requireContext(), "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}


