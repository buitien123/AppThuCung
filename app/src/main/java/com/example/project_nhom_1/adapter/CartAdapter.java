package com.example.project_nhom_1.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom_1.R;
import com.example.project_nhom_1.model.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<Product> cart;

    public CartAdapter(List<Product> cart) {
        this.cart = cart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = cart.get(position);
        holder.txtProductName.setText(product.getName());
        holder.txtProductPrice.setText(String.valueOf(product.getPrice()));

        // Decode Base64 image and load it into the ImageView using Picasso
        byte[] decodedBytes = Base64.decode(product.getImage().split(",")[1], Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        holder.imgProduct.setImageBitmap(decodedBitmap);

        // Add other views to display product details as needed
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtProductName;
        public TextView txtProductPrice;
        public ImageView imgProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
        }
    }

}
