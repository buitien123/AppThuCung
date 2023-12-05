package com.example.project_nhom_1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project_nhom_1.Fragment.UserFragment;
import com.example.project_nhom_1.HomeActicity.homeActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText eduser,edpassword;
    Button btndangnhap,btnthoat;


    private RequestQueue requestQueue;
    private String loginUrl = "http://192.168.1.2:4001/api/user/sign-in";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        btndangnhap = (Button) findViewById(R.id.login);
        eduser = (EditText)findViewById(R.id.edittextusername);
        edpassword = (EditText)findViewById(R.id.edittextpassword);

        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = eduser.getText().toString();
                String password = edpassword.getText().toString();


                try {
                    JSONObject requestBody = new JSONObject();
                    requestBody.put("email",email);
                    requestBody.put("password",password);

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, loginUrl, requestBody,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    // Xử lý phản hồi sau khi đăng nhập thành công
                                    try {
                                        String message = response.getString("message");
                                        //JSONObject jsonObject = response.getJSONObject("message");
                                        String success = response.getString("message");
                                        if (message.equals("Success")) {

                                            Toast.makeText(MainActivity.this, "kết nối thành công", Toast.LENGTH_SHORT).show();
                                            fetchUserDetails();
                                            startHomeActivity();
                                        } else {
                                            // Đăng nhập không thành công, hiển thị thông báo lỗi
                                            Toast.makeText(MainActivity.this,message, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                private void startHomeActivity() {
                                    Intent intent = new Intent(MainActivity.this, homeActivity.class);
                                    startActivity(intent);
                                    finish(); // Close the login activity
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Xử lý lỗi khi gửi yêu cầu đăng nhập không thành công
                                    Toast.makeText(MainActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                                }
                            });

                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        btnthoat = (Button) findViewById(R.id.thoat);

        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hiển thị hộp thoại xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Xác nhận thoát");
                builder.setMessage("Bạn có muốn thoát ứng dụng?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng ứng dụng nếu người dùng chọn "Có"
                        finish();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng hộp thoại nếu người dùng chọn "Không"
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void fetchUserDetails() {
        String userDetailsUrl = "http://192.168.1.2:4001/api/user/get-details/65056bec5dfb7df6fc19a45c";

        userDetailsUrl += "?timestamp=" + System.currentTimeMillis();

        JsonObjectRequest userDetailsRequest = new JsonObjectRequest(Request.Method.GET, userDetailsUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject userDetailsResponse) {
                        Log.d("UserDetailsResponse", userDetailsResponse.toString());
                        try {
                            String email = userDetailsResponse.getString("email");
                            String address = userDetailsResponse.getString("address");
                            String name = userDetailsResponse.getString("name");
                            String phone = userDetailsResponse.getString("phone");
                            String city = userDetailsResponse.getString("city");

                            UserFragment userFragment = UserFragment.newInstance(email, address, name, phone, city);

                            // Replace the current fragment with UserFragment
//                            getSupportFragmentManager().beginTransaction()
//                                    .replace(R.id.fragment_container, userFragment)
//                                    .commitAllowingStateLoss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error fetching user details", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(userDetailsRequest);
    }
}