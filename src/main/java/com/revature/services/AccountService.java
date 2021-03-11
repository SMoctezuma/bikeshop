package com.revature.services;

import com.revature.accounts.Account;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.products.Payment;
import com.revature.products.Product;

import java.util.Set;

public interface AccountService {
    // create
    public Integer registerAccount(Account a) throws NonUniqueUsernameException;
    // read
    public Account getAccountById(Integer id);
    public Account getAccountByUsername(String username);
    // update
    public void updateAccount(Account a);
    // delete
    public void deleteAccount(Account a);
    public Set<Product> getProducts(Account account);
    public void addProduct(Account account, Product product);
    public Set<Payment> getPayments(Account account);
    public Payment getPayment(Integer id);
    public void updatePayment(Payment p);
    public Set<Payment> getAllPayments();
}
