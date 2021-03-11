package com.revature.data;

import com.revature.accounts.*;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.products.Payment;
import com.revature.products.Product;
import com.revature.products.Status;
import com.revature.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;


public class AccountPostgres implements AccountDAO {
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    private Logger log = Logger.getLogger(AccountPostgres.class);

    @Override
    public Account add(Account a) throws NonUniqueUsernameException {
        Account newAccount = null;
        try (Connection conn = cu.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "insert into account values (default, ?, ?, ?)";
            //Only when you add. To get back the id.
            String[] keys = {"id"};
            PreparedStatement pstmt = conn.prepareStatement(sql, keys);

            pstmt.setString(1, a.getUsername());
            pstmt.setString(2, a.getPassword());
            pstmt.setInt(3, a.getRole().getId());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()) {
                newAccount = a;
                // you could do - rs.getInt("id")
                newAccount.setId(rs.getInt(1));
                conn.commit();
            } else {
                conn.rollback(); //Just in case it failed to insert account, to undo any mistakes.
            }
        }catch (Exception e) {
            if(e.getLocalizedMessage().contains("violates unique constraint"))
                throw new NonUniqueUsernameException();
            e.printStackTrace();
        }
        return newAccount;
    }

    @Override
    public Account getByUsername(String username) {
        Account acc = null;
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from account where username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                switch (rs.getInt("roleid")) {
                    case 1:
                        acc = new Customer();
                        acc.setId(rs.getInt("id"));
                        acc.setUsername(rs.getString("username"));
                        acc.setPassword(rs.getString("password"));
                        acc.setRole(new Role()); // Customer is default
                        return acc;
                    case 2:
                        acc = new Employee();
                        acc.setId(rs.getInt("id"));
                        acc.setUsername(rs.getString("username"));
                        acc.setPassword(rs.getString("password"));
                        Role r = new Role();
                        r.setId(2);
                        r.setName("Employee");
                        acc.setRole(r);
                        break;
                    case 3:
                        //TODO: Implement manager
                        //Manager acc = new Manager();
                        break;
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return acc;
    }

    @Override
    public Account getById(Integer id) {
        Account acc = null;
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from account where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                switch (rs.getInt("roleid")) {
                    case 1:
                        acc = new Customer();
                        acc.setId(rs.getInt("id"));
                        acc.setUsername(rs.getString("username"));
                        acc.setPassword(rs.getString("password"));
                        acc.setRole(new Role()); // Customer is default
                        break;
                    case 2:
                        acc = new Employee();
                        acc.setId(rs.getInt("id"));
                        acc.setUsername(rs.getString("username"));
                        acc.setPassword(rs.getString("password"));
                        Role r = new Role();
                        r.setId(2);
                        r.setName("Employee");
                        acc.setRole(r);
                        break;
                    case 3:
                        //TODO: Implement manager
                        //Manager acc = new Manager();
                        break;
                }
            }
        }catch(Exception e) {}
        return acc;
    }

    @Override
    public Set<Account> getAll() {
        return null;
    }

    @Override
    public void update(Account account) {

    }

    @Override
    public void delete(Account account) {
    }

    @Override
    public Set<Product> getProducts(Account account) {
        Set<Product> products = new HashSet<>();
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from product where owner = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account.getId());

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Product prod = new Product();
                prod.setId(rs.getInt(1));
                prod.setName(rs.getString(2));
                Status s = new Status();
                s.setName("Sold");
                s.setId(2);
                prod.setStatus(s);
                prod.setCategory(rs.getString(4));
                products.add(prod);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void addProduct(Account account, Product p) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update product set owner = ? where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account.getId());
            pstmt.setInt(2, p.getId());

            pstmt.executeUpdate();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Payment> getPayments(Account account) {
        Set<Payment> payments = new HashSet<>();
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from payment where accountid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, account.getId());

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Payment pay = new Payment();
                pay.setOriginalPrice(rs.getDouble(1));
                pay.setAmountPaid(rs.getDouble(2));
                pay.setPay(rs.getDouble(3));
                pay.setProductId(rs.getInt(4));
                pay.setId(rs.getInt(6));
                payments.add(pay);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public Payment getPaymentById(Integer id) {
        Payment newPay = null;
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from payment where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                newPay = new Payment();
                newPay.setOriginalPrice(rs.getDouble(1));
                newPay.setAmountPaid(rs.getDouble(2));
                newPay.setPay(rs.getDouble(3));
                newPay.setProductId(rs.getInt(4));
                newPay.setId(rs.getInt(6));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return newPay;
    }

    @Override
    public void updatePayment(Payment p) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update payment set amountpaid = ? where productid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, p.getAmountPaid());
            pstmt.setInt(2, p.getProductId());

            pstmt.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public Set<Payment> getAllPayments() {
        Set<Payment> payments = new HashSet<>();
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from payment";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Payment pay = new Payment();
                pay.setOriginalPrice(rs.getDouble(1));
                pay.setAmountPaid(rs.getDouble(2));
                pay.setPay(rs.getDouble(3));
                pay.setProductId(rs.getInt(4));
                pay.setId(rs.getInt(6));
                payments.add(pay);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return payments;
    }
}
