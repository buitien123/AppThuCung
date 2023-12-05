package com.example.project_nhom_1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_nhom_1.model.CartManager;
import com.example.project_nhom_1.model.Order;
import com.example.project_nhom_1.model.Product;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity implements CartUpdateListener {

    private ImageView imgProduct;
    private TextView productNameTextView;
    private TextView productDescriptionTextView;
    private TextView productPriceTextView;

    private Button btnAddToCart;
    private ImageButton ProductBack;

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imgProduct = findViewById(R.id.detail_imgProduct);
        productNameTextView = findViewById(R.id.detail_name);
        productDescriptionTextView = findViewById(R.id.detail_description);
        productPriceTextView = findViewById(R.id.detail_price);
        ProductBack = findViewById(R.id.ProductBack);

        Intent intent = getIntent();
        if (intent != null) {
            product = (Product) intent.getSerializableExtra("PRODUCT");

            if (product != null) {
                byte[] decodedBytes = Base64.decode(product.getImage().split(",")[1], Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                imgProduct.setImageBitmap(decodedBitmap);

                productNameTextView.setText(product.getName());
                productDescriptionTextView.setText(product.getDescription());
                productPriceTextView.setText(String.valueOf(product.getPrice()));
            }

            ProductBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            OnBackPressedCallback callback = new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    finish();
                }
            };
            getOnBackPressedDispatcher().addCallback(this, callback);
        }

        btnAddToCart = findViewById(R.id.addproduct);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        Log.d("ProductDetailActivity", "addToCart method called");
        if (product != null) {
            CartManager.getInstance().addToCart(product);
            Toast.makeText(ProductDetailActivity.this, "Added to Cart: " + product.getName(), Toast.LENGTH_SHORT).show();

            // Notify CartFragment that the cart has been updated
            onCartUpdated();

            // Send the cart data to CartActivity
//            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
//            startActivity(intent);
        }
    }


    @Override
    public void onCartUpdated() {
        // Implement this method if needed
        Log.d("ProductDetailActivity", "onCartUpdated method called");
    }

}


