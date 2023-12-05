package com.example.project_nhom_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.project_nhom_1.adapter.AdapterPet;
import com.example.project_nhom_1.adapter.AdapterProduct;
import com.example.project_nhom_1.api.Api;
import com.example.project_nhom_1.api.ApiPet;
import com.example.project_nhom_1.model.Pet;
import com.example.project_nhom_1.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pet extends AppCompatActivity {

    private RecyclerView rcvPet;
    private List<Pet> mListPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet);

        rcvPet = findViewById(R.id.rcvpet);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvPet.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvPet.addItemDecoration(itemDecoration);

        mListPet = new ArrayList<>();
        callApiGetPet();
    }

    private void callApiGetPet(){
        ApiPet.apiPet.getListPet().enqueue(new Callback<List<Pet>>() {
            @Override
            public void onResponse(Call<List<Pet>> call, Response<List<Pet>> response) {
                mListPet = response.body();
                AdapterPet adapterPet = new AdapterPet(pet.this,mListPet);
                rcvPet.setAdapter(adapterPet);
            }

            @Override
            public void onFailure(Call<List<Pet>> call, Throwable t) {
                Toast.makeText(pet.this, "fail", Toast.LENGTH_SHORT).show();

            }
        });
    }


}