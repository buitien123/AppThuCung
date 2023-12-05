package com.example.project_nhom_1.api;

import com.example.project_nhom_1.adapter.TypeAdapter;
import com.example.project_nhom_1.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface Api {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    Api api =new Retrofit.Builder()
//            10.70.89.27
//            192.168.1.2
//            172.20.10.2
//            192.168.1.108
            .baseUrl("http://192.168.1.2:4001/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(Api.class);

    @GET("api/product/get-all")
    Call<List<Product>> getListProduct();

    Gson gsonWithTypeAdapter = new GsonBuilder()
            .registerTypeAdapter(Product.class, new TypeAdapter())
            .create();

    // Retrofit instance with custom Gson instance
    Retrofit retrofitWithTypeAdapter = new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:4001/")
            .addConverterFactory(GsonConverterFactory.create(gsonWithTypeAdapter))
            .build();

    // API interface with custom Gson instance
    Api apiWithTypeAdapter = retrofitWithTypeAdapter.create(Api.class);

}
