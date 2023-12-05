package com.example.project_nhom_1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom_1.PetDetailActivity;
import com.example.project_nhom_1.ProductDetailActivity;
import com.example.project_nhom_1.R;
import com.example.project_nhom_1.model.Pet;
import com.example.project_nhom_1.model.Product;

import java.util.ArrayList;
import java.util.List;

public class AdapterPet extends RecyclerView.Adapter<AdapterPet.petViewHolder> {

    private List<Pet> mListPet;
    private final Context mContext;

    public AdapterPet(Context context, List<Pet> mListPet) {
        this.mContext = context;
        this.mListPet = mListPet != null ? mListPet : new ArrayList<>();
    }

    @NonNull
    @Override
    public petViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet,parent,false);
        return new petViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull petViewHolder holder, int position) {
        Pet pet = mListPet.get(position);
        if (pet == null) {
            return;
        }
        holder.namepet.setText(pet.getName());

        // Decode base64 string to byte array
        byte[] decodedBytes = android.util.Base64.decode(pet.getImage().split(",")[1], android.util.Base64.DEFAULT);

        // Create Bitmap from byte array
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        // Set the Bitmap to ImageView
        holder.imgpet.setImageBitmap(decodedBitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPetDetailActivity(pet);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mListPet != null){
            return mListPet.size();
        }
        return 0;
    }

    private void openPetDetailActivity(Pet pet) {
        Intent intent = new Intent(mContext, PetDetailActivity.class);
        intent.putExtra("PET",pet);
        mContext.startActivity(intent);
    }

    public class petViewHolder extends RecyclerView.ViewHolder{
        private TextView namepet;
        private ImageView imgpet;

        public petViewHolder(@NonNull View itemView) {
            super(itemView);

            namepet = itemView.findViewById(R.id.namepet);
            imgpet = itemView.findViewById(R.id.imgpet);
        }
    }

}
