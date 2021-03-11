package com.revature.products;

import com.revature.accounts.Customer;

import java.util.Objects;

public class Payment {
    private Integer id;
    private Double originalPrice = 0.00;
    private Double amountPaid = 0.00;
    private Double pay = 0.00;
    private Integer productId;
    private String product;

    public Payment() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOriginalPrice(Double price) {
        this.originalPrice = price;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    public Double getOriginalPrice() {
        return originalPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(originalPrice, payment.originalPrice) && Objects.equals(amountPaid, payment.amountPaid) && Objects.equals(pay, payment.pay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalPrice, amountPaid, pay);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "Id=" + id +
                ", product=" + product +
                ", originalPrice=" + originalPrice +
                ", amountPaid=" + amountPaid +
                ", pay=" + pay +
                '}';
    }
}
