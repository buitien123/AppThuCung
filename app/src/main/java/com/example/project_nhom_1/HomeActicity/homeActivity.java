package com.example.project_nhom_1.HomeActicity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.project_nhom_1.Fragment.CartFragment;
import com.example.project_nhom_1.Fragment.HomeFragment;
import com.example.project_nhom_1.Fragment.SearchFragment;
import com.example.project_nhom_1.Fragment.UserFragment;
import com.example.project_nhom_1.MainActivity;
import com.example.project_nhom_1.Product;
import com.example.project_nhom_1.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class homeActivity extends AppCompatActivity {
    private static final int HOME_ID = R.id.homes;
    private static final int SEARCH_ID = R.id.search;
    private static final int CART_ID = R.id.cart;
    private static final int USER_ID = R.id.user;
    ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    ImageButton product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.chipNaviga);
        chipNavigationBar.setItemSelected(R.id.homes,true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                if (i == R.id.homes) {
                    fragment = new HomeFragment();
                } else if (i == R.id.search) {
                    fragment = new SearchFragment();
                } else if (i == R.id.cart) {
                    fragment = new CartFragment();
                } else if (i == R.id.user) {
                    fragment = new UserFragment();
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }
            }
        });

    }

}