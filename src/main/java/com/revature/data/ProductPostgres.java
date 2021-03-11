package com.revature.data;

import com.revature.accounts.Customer;
import com.revature.products.Offer;
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

public class ProductPostgres implements ProductDAO {
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    private Logger log = Logger.getLogger(AccountPostgres.class);

    @Override
    public Product getById(Integer id) {
        Product prod = null;
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from product where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()) {
                prod = new Product();
                prod.setId(rs.getInt("id"));
                prod.setName(rs.getString("name"));
                prod.setStatus(new Status());
                prod.setCategory("Bicycles"); // Customer is default
            }
        }catch(Exception e) {}
        return prod;
    }

    @Override
    public Set<Product> getAll() {
        Set<Product> products = new HashSet<>();
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from product";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Product prod = new Product();
                prod.setId(rs.getInt(1));
                prod.setName(rs.getString(2));
                prod.setStatus(new Status()); //default is Available.
                prod.setCategory(rs.getString(4));
                products.add(prod);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void update(Product product) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update product set name = ?, statusid = ?, category = ? where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, product.getName());
            pstmt.setInt(2, product.getStatus().getId());
            pstmt.setString(3, product.getCategory());

            int rowsAffected = pstmt.executeUpdate();

            if(!(rowsAffected > 0)) {
                conn.rollback();
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Product product) {
        try (Connection conn = cu.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "delete from product where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, product.getId());

            int row = pstmt.executeUpdate();
            if(row > 0)
                conn.commit();
            else
                conn.rollback();
            //TODO: Check rows affected, if not rollback.

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product add(Product p) {
        Product newProduct = null;
        try (Connection conn = cu.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "insert into product values (default, ?, ?, ?)";
            //Only when you add. To get back the id.
            String[] keys = {"id"};
            PreparedStatement pstmt = conn.prepareStatement(sql, keys);

            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getStatus().getId());
            pstmt.setString(3, p.getCategory());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()) {
                newProduct = p;
                // you could do - rs.getInt("id")
                newProduct.setId(rs.getInt(1));
                conn.commit();
            } else {
                conn.rollback(); //Just in case it failed to insert account, to undo any mistakes.
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return newProduct;
    }

    @Override
    public Set<Product> getAvailableProducts() {
        Set<Product> products = new HashSet<>();
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from product where statusid = 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                Product prod = new Product();
                prod.setId(rs.getInt(1));
                prod.setName(rs.getString(2));
                prod.setStatus(new Status()); //default is Available.
                prod.setCategory(rs.getString(4));
                products.add(prod);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void acceptOffer(Offer o) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update offer set statusid = 5 where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.executeUpdate();

            sql = "update offer set statusid = 4 where id <> ? and productid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.setInt(2, o.getProductId());
            pstmt.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rejectOffer(Offer o) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update offer set statusid = 4 where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.executeUpdate();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOffer(Offer o) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update offer set offer = "+o.getOffer()+" where id = " + o.getId();
            Statement stmt = conn.createStatement();
            stmt.executeQuery(sql);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addOffer(Product p, Offer o) {
        try (Connection conn = cu.getConnection()) {
            String sql = "insert into offer values (default, ?, ?, ?, ?)";

            //Only when you add. To get back the id.
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setDouble(1, o.getOffer());
            pstmt.setInt(2, o.getStatus().getId());
            pstmt.setInt(3, p.getId());
            pstmt.setInt(4, o.getAccountId());

            pstmt.executeUpdate();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Offer getOfferById(Integer id) {
        Offer o = new Offer();
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from offer where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();;

            while(rs.next()) {
                o.setOffer(rs.getDouble(2));
                o.setId(rs.getInt(1));
                o.setStatus(new Status());
                o.setProductId(rs.getInt(4));
                o.setAccountId(rs.getInt(5));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return o;
    }

    public void updateProduct(Product p) {
        try (Connection conn = cu.getConnection()) {
            String sql = "update product set name = ? , category = ? where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getName());
            pstmt.setString(2, p.getCategory());
            pstmt.setInt(3, p.getId());
            int rows = pstmt.executeUpdate();;

            if(!(rows > 0))
                conn.rollback();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    public Set<Offer> getOffers(Product p) {
        Set<Offer> offers = new HashSet<>();
        try (Connection conn = cu.getConnection()) {
            String sql = "select * from offer where productid = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, p.getId());
            ResultSet rs = pstmt.executeQuery();;

            while(rs.next()) {
                Offer o = new Offer();
                o.setOffer(rs.getDouble(2));
                o.setId(rs.getInt(1));
                Status s = new Status();
                switch (rs.getInt(3)) {
                    case 3:
                        s.setId(3);
                        s.setName("Pending");
                        break;
                    case 4:
                        s.setId(4);
                        s.setName("Rejected");
                        break;
                    case 5:
                        s.setId(5);
                        s.setName("Accepted");
                        break;
                }
                o.setStatus(s); //default
                o.setAccountId(rs.getInt(4));
                offers.add(o);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return offers;
    }
}
