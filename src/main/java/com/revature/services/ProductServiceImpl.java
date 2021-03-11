package com.revature.services;

import com.revature.data.AccountDAO;
import com.revature.data.DAOFactory;
import com.revature.data.ProductDAO;
import com.revature.exceptions.NoCategoriesFoundException;
import com.revature.products.Offer;
import com.revature.products.Product;
import org.apache.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

public class ProductServiceImpl implements ProductService {
    //DAO's
    private AccountDAO accountDAO;
    private ProductDAO productDAO;

    private static Logger log = Logger.getLogger(AccountServiceImpl.class);

    public ProductServiceImpl() {
        productDAO = DAOFactory.getProductDAO();
        accountDAO = DAOFactory.getAccountDAO();
    }

    @Override
    public Integer addProduct(Product p) {
        return productDAO.add(p).getId();
    }

    @Override
    public Product getProductById(Integer id) {
        return productDAO.getById(id);
    }

    @Override
    public Set<Product> getAllProducts() {
        return productDAO.getAvailableProducts();
    }

    @Override
    public Set<Product> getAvailableProducts(String category) throws NoCategoriesFoundException {
        Set<Product> specificList = new HashSet<>();
        for(Product p : productDAO.getAvailableProducts()) {
            if(category.equalsIgnoreCase(p.getCategory()))
                specificList.add(p);
        }
        if(specificList.isEmpty())
            throw new NoCategoriesFoundException();
        return specificList;
    }

    @Override
    public void updateProduct(Product p) {
        productDAO.updateProduct(p);
    }

    @Override
    public void removeProduct(Product p) {
        if(getProductById(p.getId()) != null)
            productDAO.delete(p);
        else
            log.debug("Product not in database.");
    }

    @Override
    public boolean offerProduct(Integer productId, Offer o) {
        Product p = productDAO.getById(productId);
        //Gets all offers for this specific product.
        Set<Offer> offers = productDAO.getOffers(p);
        //Set offers = p.getOffers();
        //Check if the same person has made an offer on the same product. If so, override their previous offer.
        for(Offer same : offers) {
            if(same.getAccountId().equals(o.getAccountId())) {
                same.setOffer(o.getOffer());
                productDAO.updateOffer(same);
                return true;
            }
        }
        offers.add(o);
        productDAO.addOffer(p, o);
        return true;
    }

    @Override
    public Set<Offer> getOffers(Product p) {
        return productDAO.getOffers(p);
    }

    @Override
    public Offer getOfferById(Integer id) {
        return productDAO.getOfferById(id);
    }

    @Override
    public void acceptOffer(Offer o) {
        productDAO.acceptOffer(o);
    }

    @Override
    public void rejectOffer(Offer o) {
        productDAO.rejectOffer(o);
    }
}
