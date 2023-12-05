package com.example.project_nhom_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.project_nhom_1.adapter.AdapterProduct;
import com.example.project_nhom_1.api.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Product extends AppCompatActivity {

    private RecyclerView rcvProduct;
    private List<com.example.project_nhom_1.model.Product> mListProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        rcvProduct = findViewById(R.id.rcv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvProduct.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvProduct.addItemDecoration(itemDecoration);

        mListProduct = new ArrayList<>();
        callApiGetProduct();

    }

    private void callApiGetProduct(){
        Api.api.getListProduct().enqueue(new Callback<List<com.example.project_nhom_1.model.Product>>() {
            @Override
            public void onResponse(Call<List<com.example.project_nhom_1.model.Product>> call, Response<List<com.example.project_nhom_1.model.Product>> response) {
                mListProduct = response.body();
                AdapterProduct adapterProduct = new AdapterProduct(Product.this,mListProduct);
                rcvProduct.setAdapter(adapterProduct);
            }

            @Override
            public void onFailure(Call<List<com.example.project_nhom_1.model.Product>> call, Throwable t) {
                Toast.makeText(Product.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }


}