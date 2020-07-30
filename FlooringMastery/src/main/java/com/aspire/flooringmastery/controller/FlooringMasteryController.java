/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.controller;

import com.aspire.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.service.FlooringMasteryCustomerNameException;
import com.aspire.flooringmastery.service.FlooringMasteryInvalidStateException;
import com.aspire.flooringmastery.service.FlooringMasteryServiceLayer;
import com.aspire.flooringmastery.service.FlooringmasteryInvalidAreaException;
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

        while (keepGoing) {

            try {
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
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

            } catch (FlooringMasteryCustomerNameException | FlooringmasteryInvalidAreaException | FlooringMasteryInvalidStateException | FlooringMasteryPersistenceException e) {

                System.out.println("e " + e.getLocalizedMessage());

                System.out.println("e " + e.getClass());

                showErrorMsg(e.getMessage());
            }

        }

        exitMessage();
    }

    private int getMenuSelection() {

        return view.printMenuAndGetSelection();
    }

    private void displayOrders() throws FlooringMasteryPersistenceException {
        view.displayAllOrdersBanner();

        //get date of order
        String date = view.getOrderDateNoRestriction();

        //get all orders from file
        List<Order> allOrders = service.getAllOrders(date);

        //order size
        int orderSize = allOrders.size();

        //display results
        view.displayQuerying(date, orderSize);

        if (orderSize != 0) {
            view.displayAllOrders(allOrders, date);
        }

    }

    private void addOrder() throws FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException {
        view.displayAddOrderBanner();

        List<Product> allProducts = service.getAllProducts();

        List<String> allStates = service.getAllStates();

        //no products to sell
        if (allProducts.size() == 0) {

            view.displayOutOfProducts();

        } else {
            String orderDate = "07/31/2021";

            Order orderDetails = view.getOrderDetails(allProducts, allStates);

            Order orderDetailsSummary = service.calculateCosts(orderDetails);
            boolean orderToPlace = view.displayOrderDetails(orderDetailsSummary, orderDate, true);

            if (orderToPlace) {

                service.addOrder(orderDetails);

            } else {

                view.displayGoToMainMenu();

            }
        }

    }

    private void editOrder() throws FlooringMasteryPersistenceException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringmasteryInvalidAreaException {

        view.displayEditOrderBanner();

        List<Product> allProducts = service.getAllProducts();

        List<String> allStates = service.getAllStates();

        String orderDate = view.getOrderDateNoRestriction();

        Integer orderNumber = view.getOrderNumber();

        Order foundOrder = service.getOrder(orderDate, orderNumber);

        if (foundOrder != null) {

            boolean editOrder = view.displayFoundOrder(foundOrder, orderDate);

            if (editOrder) {

                Order returnedOrder = view.displayOrderToEdit(foundOrder, allStates, allProducts);

                if (foundOrder.equals(returnedOrder)) {
                    System.out.println(" same nothing chnaged");

                    view.displayEditOrderDetails(returnedOrder, orderDate);

                } else {
                    //calculate changes
                    Order updatedOrder = service.calculateCosts(returnedOrder);

                    System.out.println("things chnaged  we calculate now");
                    //display changes

                    boolean orderToUpdate = view.displayOrderDetails(updatedOrder, orderDate, false);
                    if (orderToUpdate) {
                        service.editOrder(updatedOrder, orderNumber, orderDate);
                    } else {
                        view.displayGoToMainMenu();
                    }
                }
            } else {
                view.displayGoToMainMenu();
            }
        } else {

            view.displayOrderNotFound();
        }

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
