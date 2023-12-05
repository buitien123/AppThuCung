package com.example.project_nhom_1.adapter;

import android.util.Base64;

import com.example.project_nhom_1.model.Pet;
import com.example.project_nhom_1.model.Product;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;

public class TypeAdapterPet extends TypeAdapter<Pet> {

    @Override
    public void write(JsonWriter out, Pet value) throws IOException {
        // This method is used for serialization (not needed in your case)
        throw new UnsupportedOperationException("Serialization is not supported");
    }

    @Override
    public Pet read(JsonReader in) throws IOException {
        // This method is used for deserialization

        // Check if the next token is the beginning of an object
        if (in.peek() == JsonToken.BEGIN_OBJECT) {
            // Start reading the object
            in.beginObject();
            Pet pet = new Pet();

            while (in.hasNext()) {
                // Read the name of the next property
                String name = in.nextName();
                switch (name) {
                    case "name":
                        pet.setName(in.nextString());
                        break;
                    case "color":
                        pet.setColor(in.nextString());
                        break;
                    case "description":
                        pet.setDescription(in.nextString());
                        break;
                    case "breed":
                        pet.setBreed(in.nextString());
                        break;
                    case "price":
                        pet.setPrice(in.nextInt());
                        break;
                    case "image":
                        // Read the image as a String and convert it to a byte array
                        String imageData = in.nextString();
                        byte[] decodedBytes = Base64.decode(imageData, Base64.DEFAULT);
                        pet.setImage(Arrays.toString(decodedBytes));
                        break;
                    default:
                        // Ignore unknown properties
                        in.skipValue();
                        break;
                }
            }

            // End reading the object
            in.endObject();
            return pet;
        } else {
            // If it's not the beginning of an object, skip the value
            in.skipValue();
            return null;
        }
    }



}
