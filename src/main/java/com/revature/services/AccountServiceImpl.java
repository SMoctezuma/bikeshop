package com.revature.services;

import com.revature.accounts.Account;
import com.revature.accounts.Customer;
import com.revature.data.AccountDAO;
import com.revature.data.DAOFactory;
import com.revature.data.ProductDAO;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.products.Payment;
import com.revature.products.Product;
import org.apache.log4j.Logger;

import java.util.Set;

public class AccountServiceImpl implements AccountService {
    //DAO's
    private AccountDAO accountDAO;
    private ProductDAO productDAO;

    private static Logger log = Logger.getLogger(AccountServiceImpl.class);

    public AccountServiceImpl() {
        accountDAO = DAOFactory.getAccountDAO();
        productDAO = DAOFactory.getProductDAO();
    }


    @Override
    public Integer registerAccount(Account a) throws NonUniqueUsernameException {
        return accountDAO.add(a).getId();
    }

    @Override
    public Account getAccountById(Integer id) {
        return accountDAO.getById(id);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountDAO.getByUsername(username);
    }

    @Override
    public Set<Product> getProducts(Account account) {
        return accountDAO.getProducts(account);
    }
    @Override
    public void addProduct(Account account, Product product) {
        accountDAO.addProduct(account, product);
    }

    @Override
    public void updateAccount(Account a) {

    }

    @Override
    public void deleteAccount(Account a) {

    }

    @Override
    public Set<Payment> getPayments(Account account) {
        return accountDAO.getPayments(account);
    }

    @Override
    public Payment getPayment(Integer id) {
        return accountDAO.getPaymentById(id);
    }
    @Override
    public void updatePayment(Payment p) {
        accountDAO.updatePayment(p);
    }
    @Override
    public Set<Payment> getAllPayments() {
        return accountDAO.getAllPayments();
    }
}
