package com.example.project_nhom_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_nhom_1.Fragment.HomeFragment;
import com.example.project_nhom_1.HomeActicity.homeActivity;
import com.example.project_nhom_1.adapter.OrderProductAdapter;
import com.example.project_nhom_1.model.Order;

import java.util.List;

public class OrderStatusActivity extends AppCompatActivity {

    private TextView totalPriceTextView;
    private ListView productListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        productListView = findViewById(R.id.productListView);

        Button cancelOrderButton = findViewById(R.id.cancelOrderButton);
        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the "Cancel Order" button click
                showCancelOrderConfirmationDialog();
            }
        });

        // Nhận dữ liệu đơn hàng từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            Order order = (Order) intent.getSerializableExtra("ORDER");

            // Hiển thị thông tin đơn hàng trên giao diện
            displayOrderInformation(order);
        }
    }

    private void displayOrderInformation(Order order) {
        if (order != null) {
            double totalPrice = order.getTotalPrice();
            totalPriceTextView.setText(getString(R.string.total_price_format, totalPrice));

            // Trích xuất tên và đường dẫn ảnh từ danh sách sản phẩm
            List<String> productNames = order.getProductNames();
            List<String> productImages = order.getProductImages();

            // Tạo adapter để hiển thị danh sách sản phẩm
            OrderProductAdapter adapter = new OrderProductAdapter(this, productNames, productImages);
            productListView.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Handle the Up button click, navigate to the home page
            Intent intent = new Intent(this, homeActivity.class); // Replace MainActivity with the actual home activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCancelOrderConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel Order");
        builder.setMessage("Are you sure you want to cancel this order?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform cancellation logic
                cancelOrder();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void cancelOrder() {
        // Assuming order is obtained from the Intent
        Intent intent = getIntent();
        if (intent != null) {
            Order order = (Order) intent.getSerializableExtra("ORDER");

            // Implement the logic to cancel the order
            // This can include updating the order status in the database, etc.

            // For example, you might update the order status to "Canceled"
//            order.setStatus("Canceled");

            // Notify the user
            Toast.makeText(this, "Order canceled successfully", Toast.LENGTH_SHORT).show();

            // Update the UI with the updated order information
            displayOrderInformation(order);

            // You can also pass the updated order information back to the calling activity
            // for further processing if needed
            intent.putExtra("ORDER", order);
            setResult(RESULT_OK, intent);
        }

        // Finish the activity (optional)
        finish();
    }
}
