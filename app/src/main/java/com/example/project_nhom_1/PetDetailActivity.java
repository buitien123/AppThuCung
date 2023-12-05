package com.example.project_nhom_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_nhom_1.model.Pet;

public class PetDetailActivity extends AppCompatActivity {

    private ImageView imgPet;
    private TextView petNameTextView;
    private TextView petColor;
    private TextView petDescriptionTextView;
    private TextView petBreed;
    private TextView petPriceTextView;
    private ImageButton PetBack;
    private Button PetAddCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        imgPet = findViewById(R.id.detail_imgPet);
        petNameTextView = findViewById(R.id.detail_namePet);
        petColor = findViewById(R.id.detail_colorPet);
        petDescriptionTextView = findViewById(R.id.detail_descriptionPet);
        petBreed = findViewById(R.id.detail_breedPet);
        petPriceTextView = findViewById(R.id.detail_pricePet);
        PetBack = findViewById(R.id.PetBack);
        PetAddCart = findViewById(R.id.bt_addPet);

        Intent intent = getIntent();
        if (intent != null) {
            Pet pet = (Pet) intent.getSerializableExtra("PET");

            // Hiển thị chi tiết sản phẩm
            if (pet != null) {
                // Hiển thị hình ảnh sản phẩm
                byte[] decodedBytes = Base64.decode(pet.getImage().split(",")[1], Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                imgPet.setImageBitmap(decodedBitmap);

                // Hiển thị tên, mô tả và giá sản phẩm
                petNameTextView.setText(pet.getName());
                petColor.setText(pet.getColor());
                petDescriptionTextView.setText(pet.getDescription());
                petBreed.setText(pet.getBreed());
                petPriceTextView.setText(String.valueOf(pet.getPrice()));
            }

            PetBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle back button click
                    finish();
                }
            });

            // Handle back button press
            OnBackPressedCallback callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Add your custom logic here if needed
                    finish();
                }
            };
            getOnBackPressedDispatcher().addCallback(this, callback);
        }
    }

}
