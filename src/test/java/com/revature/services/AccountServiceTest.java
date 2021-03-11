package com.revature.services;

import com.revature.accounts.Account;
import com.revature.accounts.Customer;
import com.revature.accounts.Role;
import com.revature.data.ProductDAO;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.products.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTest {
    private static AccountService accountServ;
    private static ProductDAO productDAO;

    @BeforeAll
    public static void setup() {
        accountServ = new AccountServiceImpl();
    }

    @Test
    public void testRegisterAccount() {
        Account newAccount = new Customer();
        newAccount.setUsername("testing");
        newAccount.setPassword("tester");
        newAccount.setRole(new Role());
        try {
            accountServ.registerAccount(newAccount);
        } catch(NonUniqueUsernameException e) {
            e.printStackTrace();
        }
        assertNotNull(accountServ.getAccountByUsername(newAccount.getUsername()));
    }
    @Test
    public void testGetAccountById() {
        assertNotNull(accountServ.getAccountById(1));
    }
    @Test
    public void testGetAccountByUsername() {
        assertNotNull(accountServ.getAccountByUsername("steven"));
    }

    @Test
    public void testGetProducts() {
        Set<Product> p;
        p = accountServ.getProducts(accountServ.getAccountByUsername("steven"));
        assertTrue(p.size() > 0);
    }

    @Test
    public void addProduct() {

    }

}
