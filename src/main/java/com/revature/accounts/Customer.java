package com.revature.accounts;

import com.revature.products.Payment;
import com.revature.products.Product;

import java.util.HashSet;
import java.util.Set;

public class Customer extends Account {
    private Set<Payment> payments = new HashSet<Payment>();

    //List of products user owns
    //TODO: Change bikes to products (List of products)
    private Set<Product> products;

    public Customer() {
        products = new HashSet<Product>();
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
