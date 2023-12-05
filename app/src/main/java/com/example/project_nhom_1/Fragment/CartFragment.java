package com.example.project_nhom_1.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_nhom_1.CartUpdateListener;
import com.example.project_nhom_1.OrderStatusActivity;
import com.example.project_nhom_1.ProductDetailActivity;
import com.example.project_nhom_1.R;
import com.example.project_nhom_1.adapter.CartAdapter;
import com.example.project_nhom_1.model.CartManager;
import com.example.project_nhom_1.model.Order;
import com.example.project_nhom_1.model.Product;

import java.util.List;

public class CartFragment extends Fragment implements CartUpdateListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private Button btnClearCart;
    private TextView totalPriceTextView;

    private Button btnPlaceOrder;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewCart);
        btnClearCart = view.findViewById(R.id.btnClearCart);
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        btnPlaceOrder = view.findViewById(R.id.btOrder);

        cartAdapter = new CartAdapter(CartManager.getInstance().getCart());
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {  // Thêm sự kiện cho nút đặt hàng
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        updateTotalPrice();  // Initialize total price
        return view;
    }

    private void clearCart() {
        CartManager.getInstance().clearCart();
        updateCart();
        Toast.makeText(getContext(), "Cart cleared", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCartUpdated() {
        updateCart();
    }

    private void updateCart() {
        cartAdapter.notifyDataSetChanged();  // Update the adapter
        updateTotalPrice();  // Update the total price
        Toast.makeText(getContext(), "Cart updated", Toast.LENGTH_SHORT).show();
    }

    private void updateTotalPrice() {
        double totalPrice = CartManager.getInstance().getTotalPrice();
        totalPriceTextView.setText(getString(R.string.total_price_format, totalPrice));
    }

    private void placeOrder() {
        // Tạo đơn hàng (có thể lưu thông tin đơn hàng vào một đối tượng Order)
        Order order = createOrder();

        // Chuyển dữ liệu đơn hàng sang OrderStatusActivity
        Intent intent = new Intent(getContext(), OrderStatusActivity.class);
        intent.putExtra("ORDER", order);
        startActivity(intent);
    }

    private Order createOrder() {
        List<Product> cartItems = CartManager.getInstance().getCart();
        double totalPrice = CartManager.getInstance().getTotalPrice();

        // Tạo đối tượng Order và đặt thông tin cần thiết
        Order order = new Order(cartItems, totalPrice);
        Intent intent = new Intent(getContext(), OrderStatusActivity.class);
        intent.putExtra("ORDER", order);
        startActivity(intent);

        return order;
    }
}