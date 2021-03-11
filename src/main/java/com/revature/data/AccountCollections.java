package com.revature.data;

import com.revature.accounts.Account;
import com.revature.accounts.Customer;
import com.revature.accounts.Employee;
import com.revature.accounts.Role;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.products.Payment;
import com.revature.products.Product;

import java.util.HashSet;
import java.util.Set;

public class AccountCollections implements AccountDAO {
    private Set<Account> accounts = new HashSet<>();

    public AccountCollections() {
        Account c = new Customer();
        c.setId(1);
        c.setUsername("steven");
        c.setPassword("test");
        Role r = new Role(); // Default values are customer in constructor.
        c.setRole(r);
        accounts.add(c);

        c = new Employee();
        c.setId(2);
        c.setUsername("esteban");
        c.setPassword("test");
        r = new Role();
        r.setId(2);
        r.setName("Employee");
        c.setRole(r);
        accounts.add(c);
    }

    @Override
    public Account add(Account u) throws NonUniqueUsernameException {
        for (Account c : accounts) {
            if (c.getUsername().equals(u.getUsername())) {
                throw new NonUniqueUsernameException();
            }
        }
        u.setId(accounts.size()+1);
        accounts.add(u);
        return u;
    }

    @Override
    public Account getById(Integer id) {
        for (Account c : accounts) {
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    @Override
    public Account getByUsername(String username) {
        for (Account c : accounts) {
            if (c.getUsername().equals(username))
                return c;
        }
        return null;
    }

    @Override
    public Set<Account> getAll() {
        return accounts;
    }

    @Override
    public void update(Account u) {
        for (Account c : accounts) {
            if (c.getId() == u.getId()) {
                this.delete(c);
                accounts.add(u);
                return;
            }
        }
        //return;
    }

    @Override
    public void delete(Account t) {
        accounts.remove(t);
    }

    @Override
    public Set<Product> getProducts(Account account) {
        Set<Product> pList = new HashSet<>();
        return pList;
    }

    @Override
    public void addProduct(Account account, Product p) {

    }
    @Override
    public Set<Payment> getPayments(Account account) {
        Set<Payment> temp = new HashSet<>();
        return temp;
    }

    @Override
    public Payment getPaymentById(Integer id) {
        Payment newPay = new Payment();
        return newPay;
    }
    @Override
    public void updatePayment(Payment p) {

    }
    @Override
    public Set<Payment> getAllPayments() {
        Set<Payment> temp = new HashSet<>();
        return temp;
    }


}
