package com.example.project_nhom_1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private List<Product> products;
    private double totalPrice;

    private List<String> productNames;
    private List<String> productImages;

    public Order(List<Product> products, double totalPrice) {
        this.products = products;
        this.totalPrice = totalPrice;
        this.productNames = new ArrayList<>();
        this.productImages = new ArrayList<>();

        // Thêm thông tin sản phẩm vào danh sách
        for (Product product : products) {
            addProductInfo(product.getName(), product.getImage());
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public List<String> getProductNames() {
        return productNames;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    // Thêm thông tin sản phẩm vào đơn hàng
    public void addProductInfo(String productName, String productImage) {
        productNames.add(productName);
        productImages.add(productImage);
    }
}

