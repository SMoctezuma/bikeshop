package com.revature.products;

import java.util.Objects;

public class Offer {
    private Integer id;
    private Integer accountId;
    private Double offer = 0.00;
    private Status status;
    private Integer productId;

    public Offer() {
        this.id = 1;
        this.status = new Status();
        this.status.setName("Pending");
        this.status.setId(3); //Pending is 3.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Double getOffer() {
        return offer;
    }

    public void setOffer(Double offer) {
        this.offer = offer;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer1 = (Offer) o;
        return Objects.equals(id, offer1.id) && Objects.equals(accountId, offer1.accountId) && Objects.equals(offer, offer1.offer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, offer);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", offer=" + offer +
                ", status=" + status +
                '}';
    }
}
