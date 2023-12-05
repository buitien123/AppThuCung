package com.example.project_nhom_1.adapter;

import android.util.Base64;

import com.example.project_nhom_1.model.Product;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;

public class TypeAdapter extends com.google.gson.TypeAdapter<Product> {

    @Override
    public void write(JsonWriter out, Product value) throws IOException {
        // This method is used for serialization (not needed in your case)
        throw new UnsupportedOperationException("Serialization is not supported");
    }

    @Override
    public Product read(JsonReader in) throws IOException {
        // This method is used for deserialization

        // Check if the next token is the beginning of an object
        if (in.peek() == JsonToken.BEGIN_OBJECT) {
            // Start reading the object
            in.beginObject();
            Product product = new Product();

            while (in.hasNext()) {
                // Read the name of the next property
                String name = in.nextName();
                switch (name) {
                    case "name":
                        product.setName(in.nextString());
                        break;
                    case "description":
                        product.setDescription(in.nextString());
                        break;
                    case "price":
                        product.setPrice(in.nextInt());
                        break;
                    case "image":
                        // Read the image as a String and convert it to a byte array
                        String imageData = in.nextString();
                        byte[] decodedBytes = Base64.decode(imageData, Base64.DEFAULT);
                        product.setImage(Arrays.toString(decodedBytes));
                        break;
                    default:
                        // Ignore unknown properties
                        in.skipValue();
                        break;
                }
            }

            // End reading the object
            in.endObject();
            return product;
        } else {
            // If it's not the beginning of an object, skip the value
            in.skipValue();
            return null;
        }
    }


}
