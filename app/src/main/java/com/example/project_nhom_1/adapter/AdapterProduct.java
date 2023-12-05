package com.example.project_nhom_1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom_1.ProductDetailActivity;
import com.example.project_nhom_1.R;
import com.example.project_nhom_1.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ProductViewHodel> {

    private final List<Product> mListProduct;
    private final Context mContext;

    public AdapterProduct(Context context, List<Product> mListProduct) {
        this.mContext = context;
        this.mListProduct = mListProduct != null ? mListProduct : new ArrayList<>();
    }

    @NonNull
    @Override
    public ProductViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ProductViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHodel holder, int position) {
        Product product = mListProduct.get(position);
        if (product == null) {
            return;
        }
        holder.tvname.setText(product.getName());

        // Decode base64 string to byte array
        byte[] decodedBytes = android.util.Base64.decode(product.getImage().split(",")[1], android.util.Base64.DEFAULT);

        // Create Bitmap from byte array
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

        // Set the Bitmap to ImageView
        holder.imgProduct.setImageBitmap(decodedBitmap);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetailActivity(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null){
            return mListProduct.size();
        }
        return 0;
    }

    private void openProductDetailActivity(Product product) {
        Intent intent = new Intent(mContext, ProductDetailActivity.class);
        intent.putExtra("PRODUCT",product);
        mContext.startActivity(intent);
    }

    public class ProductViewHodel extends RecyclerView.ViewHolder{

        private TextView tvname;
        private ImageView imgProduct;
        private ImageButton ProductBack;

        //private RatingBar rating;

        public ProductViewHodel(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.name);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            ProductBack = itemView.findViewById(R.id.Bt_rcvBack);

//            ProductBack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mContext instanceof Activity) {
//                        ((Activity) mContext).onBackPressed();
//                    }
//                }
//            });
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle item click here
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Product product = mListProduct.get(position);
//                        openProductDetailActivity(product);
//                    }
//                }
//            });

        }
    }
}
