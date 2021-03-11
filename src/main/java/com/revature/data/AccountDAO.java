package com.revature.data;

import com.revature.accounts.Account;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.products.Payment;
import com.revature.products.Product;

import java.util.Set;

public interface AccountDAO extends GenericDAO<Account> {
    public Account add(Account c) throws NonUniqueUsernameException;
    public Account getByUsername(String username);
    public void addProduct(Account account, Product p);
    public Set<Product> getProducts(Account account);
    public Set<Payment> getPayments(Account account);
    public Payment getPaymentById(Integer id);
    public void updatePayment(Payment p);
    public Set<Payment> getAllPayments();
}
