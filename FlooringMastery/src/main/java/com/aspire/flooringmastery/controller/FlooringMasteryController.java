/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.controller;

import com.aspire.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.OrderDetail;
import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.service.FlooringMasteryCustomerNameException;
import com.aspire.flooringmastery.service.FlooringMasteryServiceLayer;
import com.aspire.flooringmastery.ui.FlooringMasteryView;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrder();
                        break;
                    case 2:
                        addOrder();

                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();

                        break;
                    case 5:
                        exportAllData();

                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }

        } catch (FlooringMasteryCustomerNameException | FlooringMasteryPersistenceException e) {
            showErrorMsg(e.getMessage());
        }
        exitMessage();
    }

    private int getMenuSelection() {

        return view.printMenuAndGetSelection();
    }

    private void displayOrder() throws FlooringMasteryPersistenceException {
        view.displayAllOrdersBanner();

        //get date of order
        String date = view.getOrderDateToDisplayAllOrders();

        //get all orders from file
        List<Order> allOrders = service.getAllOrders(date);

        //order size
        int orderSize = allOrders.size();

        //display results
        view.displayQuerying(date, orderSize);

        if (orderSize != 0) {
            view.displayAllOrders(allOrders);
        }

    }

    private void addOrder() throws FlooringMasteryPersistenceException, FlooringMasteryCustomerNameException {
        view.displayAddOrderBanner();

        //get date of order
        // String date = view.getOrderDate();
        String date = "07/23/1991";

        List<Product> allProducts = service.getAllProducts();

        if (allProducts.size() == 0) {

            view.displayOutOfProducts();

        } else {

            OrderDetail orderDetails = view.getOrderDetails(date, allProducts);

            service.addOrder(orderDetails);

        }

    }

    private void editOrder() {
        System.out.println("edit orders");
    }

    private void removeOrder() {
        System.out.println("remove orders");
    }

    private void exportAllData() {
        System.out.println("Export all Data");
    }

    private void unknownCommand() {
        System.out.println("Unknown command");
    }

    private void exitMessage() {
        System.out.println("goodbye");
    }

    private void showErrorMsg(String errorMsg) {
        view.displayErrorMessage(errorMsg);
    }

}
