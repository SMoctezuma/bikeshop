package com.revature.services;

import com.revature.exceptions.NoCategoriesFoundException;
import com.revature.products.Offer;
import com.revature.products.Product;

import java.util.Set;

public interface ProductService {
    public Integer addProduct(Product p);
    public Product getProductById(Integer id);
    public Set<Product> getAllProducts();
    public Set<Product> getAvailableProducts(String categories) throws NoCategoriesFoundException;
    public void updateProduct(Product p);
    public void removeProduct(Product p);
    public boolean offerProduct(Integer productId, Offer o);
    public Set<Offer> getOffers(Product p);
    public Offer getOfferById(Integer id);
    public void acceptOffer(Offer o);
    public void rejectOffer(Offer o);
}
