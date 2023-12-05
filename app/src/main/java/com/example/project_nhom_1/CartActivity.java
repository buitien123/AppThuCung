package com.example.project_nhom_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.project_nhom_1.Fragment.CartFragment;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CartFragment())  // Use the ID of your container
                    .commit();
        }
    }
}