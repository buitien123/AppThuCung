package com.example.project_nhom_1.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.project_nhom_1.OrderStatusActivity;
import com.example.project_nhom_1.Product;
import com.example.project_nhom_1.R;
import com.example.project_nhom_1.pet;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Tìm ImageButton theo ID
        ImageButton categoryDogButton = view.findViewById(R.id.categoryDog);
        ImageButton petcat = view.findViewById(R.id.petcat);
        ImageButton petdog = view.findViewById(R.id.petdog);

        // Thêm sự kiện click cho ImageButton
        categoryDogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang Activity mới
                Intent intent = new Intent(getActivity(), Product.class);
                startActivity(intent);
            }
        });

        petcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang Activity mới
                Intent intent = new Intent(getActivity(), pet.class);
                startActivity(intent);
            }
        });

        petdog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển sang Activity mới
                Intent intent = new Intent(getActivity(), OrderStatusActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }




}