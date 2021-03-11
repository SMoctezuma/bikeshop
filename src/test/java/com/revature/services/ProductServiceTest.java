package com.revature.services;

import com.revature.data.AccountDAO;
import com.revature.data.AccountPostgres;
import com.revature.data.ProductDAO;
import com.revature.products.Product;
import com.revature.products.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {
    private static ProductService productServ;
    private static ProductDAO accountDao;

    @BeforeAll
    public static void setup() {
        productServ = new ProductServiceImpl();
    }

    @Test
    public void testGetAvailableProducts() {
        try {
            Set<Product> availableProducts = productServ.getAvailableProducts("Bicycles");
            for(Product p : availableProducts) {
                Status s = p.getStatus();
                assertEquals("Available", s.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetProductById() {
        assertNotNull(productServ.getProductById(5)); //assume it returns an object back.
    }

    @Test
    public void testAddProduct() {
        int origSize = productServ.getAllProducts().size();
        Product p = new Product();
        p.setCategory("Bicycles");
        //Change this name to something diff every run.
        p.setName("New bike 2.1");
        p.setStatus(new Status());
        productServ.addProduct(p);
        int newSize = productServ.getAllProducts().size();

        assertTrue(newSize > origSize);
    }

    @Test
    public void testGetAllProducts() {

    }

    @Test
    public void testOfferProduct() {

    }
}
