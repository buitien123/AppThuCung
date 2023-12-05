package com.example.project_nhom_1.model;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private List<Product> cart;

    private CartManager() {
        cart = new ArrayList<>();
    }

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void addToCart(Product product) {
        cart.add(product);
    }

    public void clearCart() {
        cart.clear();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : cart) {
            total += product.getPrice();
        }
        return total;
    }

}
