package com.revature.data;

import com.revature.accounts.Customer;
import com.revature.products.Offer;
import com.revature.products.Product;
import com.revature.products.Status;

import java.util.HashSet;
import java.util.Set;

public class ProductCollections implements ProductDAO {
    private static Set<Product> products = new HashSet<>();

    public ProductCollections() {
        Product p = new Product();
        p.setId(1);
        p.setName("Bike 1");
        products.add(p);

        p = new Product();
        p.setId(2);
        p.setName("Bike 2");
        products.add(p);

        p = new Product();
        p.setId(3);
        p.setName("Bike 3");
        Set SoF = new HashSet<Offer>();
        Offer o = new Offer();
        o.setOffer(2.75);
        o.setAccountId(1);
        SoF.add(o);
        o = new Offer();
        o.setId(2);
        o.setAccountId(2);
        o.setOffer(3.99);
        SoF.add(o);
        //p.setOffers(SoF);
        products.add(p);
    }
    @Override
    public Product add(Product p) {
        products.add(p);
        return p;
    }

    @Override
    public Product getById(Integer id) {
        for(Product p : products) {
            if(p.getId().equals(id))
                return p;
        }
        return null;
    }
    @Override
    public Set<Product> getAll() {
        return products;
    }

    @Override
    public Set<Product> getAvailableProducts() {
        Set<Product> availableProducts = new HashSet<>();
        for(Product p: products) {
            if(p.getStatus().getName().equalsIgnoreCase("Available")) {
                availableProducts.add(p);
            }
        }
        return availableProducts;
    }

    @Override
    public void update(Product p) {
        Product original = getById(p.getId());
        if(original != null) {
            original.setId(p.getId());
            original.setName(p.getName());
            original.setCategory(p.getCategory());
            original.setStatus(p.getStatus());
        }
    }

    @Override
    public void delete(Product p) {
        if(products.contains(p))
            products.remove(p);
    }

    @Override
    public void acceptOffer(Offer o) {
        //TODO:
    }

    @Override
    public void rejectOffer(Offer o) {
        //TODO:
    }

    @Override
    public Set<Offer> getOffers(Product p) {
        return null;
    }

    @Override
    public void updateOffer(Offer o) {

    }

    @Override
    public void addOffer(Product p, Offer o) {

    }

    @Override
    public Offer getOfferById(Integer id) {
        Offer o = new Offer();
        return o;
    }
    @Override
    public void updateProduct(Product p) {

    }
}
