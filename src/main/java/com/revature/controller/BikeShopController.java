package com.revature.controller;

import com.revature.accounts.Account;
import com.revature.accounts.Customer;
import com.revature.accounts.Role;
import com.revature.exceptions.NoCategoriesFoundException;
import com.revature.exceptions.NonUniqueUsernameException;
import com.revature.products.Offer;
import com.revature.products.Payment;
import com.revature.products.Product;
import com.revature.products.Status;
import com.revature.services.AccountService;
import com.revature.services.AccountServiceImpl;
import com.revature.services.ProductService;
import com.revature.services.ProductServiceImpl;
import org.apache.log4j.Logger;

import java.util.Set;

import static com.revature.utils.User.*;

public class BikeShopController {
    private static Logger log = Logger.getLogger(BikeShopController.class);
    private static AccountService accountServ = new AccountServiceImpl();
    private static ProductService productServ = new ProductServiceImpl();

    public static void main(String[] args) {
        boolean userActive = true;

        app: while(userActive) {
            System.out.println("===================================\n"+
                    "|  Welcome to Steven\'s Bike Shop  |\n"+
                    "===================================\n");
            Account userAccount = null;

            while(userAccount == null) {
                String userInput = askUser("What would you like to do?\n1. Register\n2. Log in");
                switch (userInput) {
                    case "1":
                        log.info("User is registering a new account");
                        userAccount = register();
                        break;
                    case "2":
                        log.info("User is logging in.");
                        userAccount = login();
                        log.debug(userAccount);
                        break;
                    default:
                        log.info("User leaving!");
                        userActive = false;
                        break app;
                }
            }
            System.out.println("Welcome "+ userAccount.getUsername()+",");
            menuForCustomers: while(true) {
                if(!userAccount.getRole().getName().equals("Customer"))
                    break menuForCustomers;
                String userInput = askUser("What would you like to do next?\n1. View available bicycles\n2. View my bicycles\n3. Payments on bicycles");
                //TODO: Bike accessories.
                switch (userInput) {
                    case "1":
                        log.info(userAccount.getUsername() +" : is viewing available bikes");
                        userAccount = viewAvailableBicycles(((Customer)userAccount));
                        break;
                    case "2":
                        viewUserProducts(((Customer)userAccount));
                        break;
                    case "3":
                        viewPayments(((Customer)userAccount));
                        break;
                    default:
                        log.info("User leaving!");
                        userActive = false;
                        break app;
                }
            }

            menuForEmployees: while(userAccount != null) {
                if(!userAccount.getRole().getName().equals("Employee"))
                    break menuForEmployees;
                System.out.println("Welcome " + userAccount.getUsername() + " to the employee panel,");
                String userInput = askUser("What would you like to do next?\n1. View available products\n2. View all payments\nOther to log out");
                switch (userInput) {
                    case "1":
                        try {
                            System.out.println(productServ.getAvailableProducts("Bicycles"));
                            String pQ = askUser("1. Select Product\n2. Add a new product\n3. Remove a product\n4. Edit a product\nOther to go back");
                            switch (pQ) {
                                case "1":
                                    String pID = askUser("What is the product id you wish to select?");
                                    System.out.println(productServ.getProductById(Integer.valueOf(pID)));
                                    System.out.println("Offers: "+ productServ.getOffers(productServ.getProductById(Integer.valueOf(pID))));
                                    String vO = askUser("1. Accept an offer.\n2. Reject an offer.\n3. Go back");
                                    switch (vO) {
                                        case "1":
                                            String userOffer = askUser("Type the offer id you wish to accept.");
                                            Offer usOff = productServ.getOfferById(Integer.valueOf(userOffer));
                                            productServ.acceptOffer(usOff);
                                            System.out.println("Accepted, " + accountServ.getAccountById(usOff.getAccountId()).getUsername() + " owns " + productServ.getProductById(usOff.getProductId()).getName());
                                            accountServ.addProduct(accountServ.getAccountById(usOff.getAccountId()), productServ.getProductById(usOff.getProductId()));
                                            break;
                                        case "2":
                                            String userO = askUser("Type the offer id you wish to reject.");
                                            Offer userOff = productServ.getOfferById(Integer.valueOf(userO));
                                            productServ.rejectOffer(userOff);
                                            System.out.println("Rejected, "+ userOff);
                                            break;
                                        default:
                                    }
                                    break;
                                case "2":
                                    String categoryInput = askUser("What is the category of the product?");
                                    String nameInput = askUser("What is the product name?");
                                    Product p = new Product();
                                    p.setId(9);
                                    p.setName(nameInput);
                                    p.setCategory(categoryInput);
                                    productServ.addProduct(p);
                                    break;
                                case "3":
                                    String idInput = askUser("What is the id of the product you wish to remove?");
                                    Product prodRem = productServ.getProductById(Integer.valueOf(idInput));
                                    productServ.removeProduct(prodRem);
                                    System.out.println("Removed product - "+ prodRem.getName());
                                    break;
                                case "4":
                                    String iInput = askUser("What is the id of the product you wish to edit?");
                                    Product prod = productServ.getProductById(Integer.valueOf(iInput));
                                    System.out.println("If you don't wish to change value, press [Enter] when asked.");
                                    String nameI = askUser("What is the products' new name?");
                                    String categoryI = askUser("What is the products' new category?");
                                    if(!nameI.equals(""))
                                        prod.setName(nameI);
                                    if(!categoryI.equals(""))
                                        prod.setCategory(categoryI);
                                    productServ.updateProduct(prod);
                                    System.out.println("Successfully updated: " + prod);
                                    break;
                                default:
                            }
                        } catch(NoCategoriesFoundException e) {
                            //There will always be a bike category, no need.
                        }
                        break;
                    case "2":
                        viewAllPayments();
                        break;
                    default:
                        System.exit(0);
                        break;
                }
            }

            menuForManagers: while(true) {
                if(!userAccount.getRole().getName().equals("Managers"))
                    break menuForManagers;
                String userInput = askUser("Welcome " + userAccount.getUsername() + " to the manager panel,\n"+
                        "What would you like to do next?\n1. Make employee account\n2. Fire employee\n3. History of offers");
            }
        }
    }
    public static Account register() {
        while(true) {
            Account newCustomer = new Customer();
            String username = askUser("Enter a username:");
            String password = askUser("Enter a password:");
            String infoCorrect = askUser("Is the information correct? \nUsername: "+username+"\nPassword: "+password+
                    "\n1 to confirm, 2 to start over, other to cancel");
            switch(infoCorrect) {
                case "1":
                    newCustomer.setUsername(username);
                    newCustomer.setPassword(password);
                    Role r = new Role();
                    newCustomer.setRole(r);
                    log.debug(newCustomer.toString());
                    try {
                        newCustomer.setId(accountServ.registerAccount(newCustomer));
                        log.debug(newCustomer);
                        return newCustomer;
                    }catch (NonUniqueUsernameException e) {
                        log.warn("User tried to register with a non unique username.");
                        System.out.println("Sorry, that username is taken. Try again!");
                    }
                    break;
                case "2":
                    System.out.println("Restarting...");
                    break;
                default:
                    System.out.println("Back to main menu");
                    return null;
            }

        }
    }
    public static Account login() {
        while(true) {
            String username = askUser("Enter a username:");
            String password = askUser("Enter a password:");
            Account user = accountServ.getAccountByUsername(username);
            if(user == null) {
                log.debug("User entered a username that doesn't exist.");
                System.out.println("Could not find an account with that username.");
            } else if(user.getPassword().equals(password)) {
                log.debug(user);
                return user;
            } else {
                log.debug("Invalid password");
                System.out.println("Invalid password.");
            }
            String userInput = askUser("Do you want to try again? \n1. Yes\nOther for no.");
            if(!("1").equals(userInput))
                return null;
        }
    }

    public static Account viewAvailableBicycles(Customer loggedInUser) {
        try {
            String userInput = askUser("Available bicycles: \n"+ productServ.getAvailableProducts("Bicycles")+"\n"+"Which bicycle would you like to request an offer? 0 or Other to go to back.");
            switch(userInput) {
                default:
                    productServ.getProductById(Integer.valueOf(userInput));
                    String UI = askUser("What would you like to offer for this?");
                    Offer o = new Offer();
                    o.setOffer(Double.valueOf(UI));
                    o.setProductId(Integer.valueOf(userInput));
                    o.setAccountId(loggedInUser.getId());
                    productServ.offerProduct(Integer.valueOf(userInput), o);
                    System.out.println("Your offer of "+ o.getOffer() + " has been requested.");
                    log.debug("Added offer to bicycle: "+ productServ.getProductById(Integer.valueOf(userInput)));
                case "0":
                    break;
            }
        } catch (NoCategoriesFoundException e) {
            System.out.println("No categories found");
        }
        return loggedInUser;
    }

    public static void viewUserProducts(Customer loggedInUser) {
        System.out.println("Your products: ");
        System.out.println(accountServ.getProducts(loggedInUser)+ " \n");
    }

    public static boolean viewPayments(Customer account) {
        Set<Payment> payments = accountServ.getPayments(account);
        for(Payment p : payments) {
            p.setProduct(productServ.getProductById(p.getProductId()).getName());
        }
        System.out.println("Payments: " + payments);
        String userInput = askUser("Type the id of the payment you wish to select. \n0. To go back.");
        if("0".equals(userInput))
            return true;
        Payment userPayment = accountServ.getPayment(Integer.valueOf(userInput));
        userPayment.setProduct(productServ.getProductById(userPayment.getProductId()).getName());
        System.out.println(userPayment);
        String ui = askUser("Do you wish to make a payment of $50? (Weeks left: "+ calcPayment(userPayment.getOriginalPrice()-userPayment.getAmountPaid(), 50.0)+")\n1. Yes\nOther to go back");
        if("1".equals(ui)) {
            Payment p = userPayment;
            p.setPay(50.0);
            p.setAmountPaid(p.getPay() + p.getAmountPaid());
            accountServ.updatePayment(p);
            System.out.println("Payment added.");
        }
        return true;
    }
    public static int calcPayment(double p, double minimumDeposit) {
        return (int)(p/minimumDeposit);
    }
    public static void viewAllPayments() {
        Set<Payment> payments = accountServ.getAllPayments();
        for(Payment p : payments) {
            p.setProduct(productServ.getProductById(p.getProductId()).getName());
        }
        System.out.println("All payments: "+ payments);
    }
}
