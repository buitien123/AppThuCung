package com.example.project_nhom_1.api;

import com.example.project_nhom_1.adapter.TypeAdapter;
import com.example.project_nhom_1.adapter.TypeAdapterPet;
import com.example.project_nhom_1.model.Pet;
import com.example.project_nhom_1.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiPet {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyy").create();

    ApiPet apiPet =new Retrofit.Builder()
//            10.70.89.27
//            192.168.1.2
//            172.20.10.2
            .baseUrl("http://192.168.1.2:4001/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiPet.class);

    @GET("api/pets/all")
    Call<List<Pet>> getListPet();

    Gson gsonWithTypeAdapter = new GsonBuilder()
            .registerTypeAdapter(Pet.class, new TypeAdapterPet())
            .create();

    // Retrofit instance with custom Gson instance
    Retrofit retrofitWithTypeAdapter = new Retrofit.Builder()
            .baseUrl("http://192.168.1.2:4001/")
            .addConverterFactory(GsonConverterFactory.create(gsonWithTypeAdapter))
            .build();

    // API interface with custom Gson instance
    ApiPet apiWithTypeAdapter = retrofitWithTypeAdapter.create(ApiPet.class);
}
