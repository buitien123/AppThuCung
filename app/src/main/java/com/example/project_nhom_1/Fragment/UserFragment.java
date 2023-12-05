package com.example.project_nhom_1.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_nhom_1.MainActivity;
import com.example.project_nhom_1.R;

public class UserFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String email = args.getString("email");
            String address = args.getString("address");
            String name = args.getString("name");
            String phone = args.getString("phone");
            String city = args.getString("city");

            Log.d("UserFragment", "Received arguments: email=" + email + ", address=" + address + ", name=" + name + ", phone=" + phone + ", city=" + city);

            TextView usernameTextView = view.findViewById(R.id.usernameTextView);
            TextView addressTextView = view.findViewById(R.id.addressTextView);
            TextView nameTextView = view.findViewById(R.id.nameTextView);
            TextView phoneTextView = view.findViewById(R.id.phoneTextView);
            TextView cityTextView = view.findViewById(R.id.cityTextView);

            if (usernameTextView != null && addressTextView != null && nameTextView != null && phoneTextView != null && cityTextView != null) {
                usernameTextView.setText("Email: " + email);
                addressTextView.setText("Address: " + address);
                nameTextView.setText("Name: " + name);
                phoneTextView.setText("Phone: " + phone);
                cityTextView.setText("City: " + city);
            } else {
                Log.e("UserFragment", "One or more TextViews is null");
            }
        } else {
            Log.e("UserFragment", "Arguments are null");
        }

        Button logoutButton = view.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the logout action here, for example, return to the login screen
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish(); // Close the Profile activity
            }
        });

        return view;
    }

    public static UserFragment newInstance(String email, String address, String name, String phone, String city) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putString("email", email);
        args.putString("address", address);
        args.putString("name", name);
        args.putString("phone", phone);
        args.putString("city", city);
        fragment.setArguments(args);
        return fragment;
    }
}
