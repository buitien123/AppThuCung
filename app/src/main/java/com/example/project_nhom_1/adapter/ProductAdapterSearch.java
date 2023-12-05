package com.example.project_nhom_1.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom_1.R;
import com.example.project_nhom_1.model.Pet;
import com.example.project_nhom_1.model.Product;

import java.util.List;

public class ProductAdapterSearch extends RecyclerView.Adapter<ProductAdapterSearch.ViewHolder>{

    private List<Object> productList;

    public ProductAdapterSearch(List<Object> productList) {
        this.productList = productList;
    }

    public void updateData(List<Object> newData) {
        productList.clear();
        productList.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object product = productList.get(position);

        if (product instanceof Product) {
            Product productItem = (Product) product;
            holder.productNameTextView.setText(productItem.getName());

            // Decode Base64 and set Bitmap to ImageView
            if (productItem.getImage() != null) {
                byte[] decodedBytes = android.util.Base64.decode(productItem.getImage().split(",")[1], android.util.Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                holder.searchimg.setImageBitmap(decodedBitmap);
            }
        } else if (product instanceof Pet) {
            Pet petItem = (Pet) product;
            holder.productNameTextView.setText(petItem.getName());

            // Decode Base64 and set Bitmap to ImageView
            if (petItem.getImage() != null) {
                byte[] decodedBytes = android.util.Base64.decode(petItem.getImage().split(",")[1], android.util.Base64.DEFAULT);
                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                holder.searchimg.setImageBitmap(decodedBitmap);
            }
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView;

        ImageView searchimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            searchimg = itemView.findViewById(R.id.imgitemSearch);
        }
    }

}
