package com.revature.data;

import com.revature.accounts.Customer;
import com.revature.products.Offer;
import com.revature.products.Product;

import java.util.Set;

public interface ProductDAO extends GenericDAO<Product> {
    public Product add(Product p);
    public Set<Product> getAvailableProducts();
    public Set<Offer> getOffers(Product p);
    public void updateOffer(Offer o);
    public void addOffer(Product p, Offer o);
    public void acceptOffer(Offer o);
    public void rejectOffer(Offer o);
    public Offer getOfferById(Integer id);
    public void updateProduct(Product p);
}
