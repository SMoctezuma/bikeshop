package com.revature.data;

public class DAOFactory {
    public static AccountDAO getAccountDAO() {
        return new AccountPostgres();
    }

    public static ProductDAO getProductDAO() {
        return new ProductPostgres();
    }

}
