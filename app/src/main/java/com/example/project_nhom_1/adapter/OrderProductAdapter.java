package com.example.project_nhom_1.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_nhom_1.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

public class OrderProductAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> productNames;
    private List<String> productImages;

    public OrderProductAdapter(Context context, List<String> productNames, List<String> productImages) {
        super(context, R.layout.item_order_product, productNames);
        this.context = context;
        this.productNames = productNames;
        this.productImages = productImages;
    }

    public void updateData(List<String> productNames, List<String> productImages) {
        this.productNames = productNames;
        this.productImages = productImages;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_order_product, parent, false);

        TextView productNameTextView = rowView.findViewById(R.id.productNameTextView);
        ImageView productImageView = rowView.findViewById(R.id.productImageView);

        productNameTextView.setText(productNames.get(position));

        // Load and display image using Picasso or another image loading library
        String base64Image = productImages.get(position);
        new LoadImageTask(productImageView).execute(base64Image);

        return rowView;
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String base64Image = params[0].replace("data:image/jpeg;base64,", "");

            byte[] imageBytes = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
            InputStream inputStream = new ByteArrayInputStream(imageBytes);
            return BitmapFactory.decodeStream(inputStream);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}
