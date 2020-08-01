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
import com.aspire.flooringmastery.service.FlooringMasteryInvalidProductTypeException;
import com.aspire.flooringmastery.service.FlooringMasteryInvalidStateException;
import com.aspire.flooringmastery.service.FlooringMasteryServiceLayer;
import com.aspire.flooringmastery.service.FlooringmasteryInvalidAreaException;
import com.aspire.flooringmastery.ui.FlooringMasteryView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author louie
 */
@Component
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    @Autowired
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

            } catch (FlooringMasteryInvalidProductTypeException | FlooringMasteryCustomerNameException | FlooringmasteryInvalidAreaException | FlooringMasteryInvalidStateException | FlooringMasteryPersistenceException e) {

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

        //get date/day of orders you want to view
        String date = view.getOrderDateNoRestriction();

        //get all orders from file in a list
        List<Order> allOrders = service.getAllOrders(date);

        //order size
        int orderSize = allOrders.size();

        //display results
        view.displayQuerying(date, orderSize);
        if (orderSize != 0) {
            view.displayAllOrders(allOrders, date);
        }

    }

    private void addOrder() throws FlooringMasteryInvalidProductTypeException, FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException {
        view.displayAddOrderBanner();

        //get all products available
        List<Product> allProducts = service.getAllProducts();
        //get allstates avaiable
        List<String> allStates = service.getAllStates();

        //no products to sell
        if (allProducts.size() == 0) {
            //display were out of products
            view.displayOutOfProducts();

        } else {
            //get date for when order is needed
            String orderDate = view.getOrderDateRestricted();
            //get order details ~> customer name, producttype, state, and area
            Order orderDetails = view.getOrderDetails(allProducts, allStates);
            //calculate costs of order
            Order orderDetailsSummary = service.calculateCosts(orderDetails);
            //ask customer to confirm order
            boolean orderToPlace = view.displayOrderDetails(orderDetailsSummary, orderDate, true);
            //if yes
            if (orderToPlace) {
                //add order
                Order order = service.addOrder(orderDetails);

                view.displayAddOrderSucess(order.getOrderNumber(), orderDate);

            } else {
                //if no
                //send to main menu
                view.displayGoToMainMenu();

            }
        }

    }

    private void editOrder() throws FlooringMasteryInvalidProductTypeException, FlooringMasteryPersistenceException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringmasteryInvalidAreaException {

        view.displayEditOrderBanner();

        //get all products available
        List<Product> allProducts = service.getAllProducts();
        //get allstates avaiable
        List<String> allStates = service.getAllStates();

        //get date/day when order was placed
        String orderDate = view.getOrderDateNoRestriction();
        //get order number
        Integer orderNumber = view.getOrderNumber();
        //find order
        Order foundOrder = service.getOrder(orderDate, orderNumber);

        //if order not null
        if (foundOrder != null) {
            //display order and ask if they want to edit
            boolean editOrder = view.displayFoundOrder(foundOrder, orderDate, false);

            //if yes
            if (editOrder) {
                //let them make changes
                Order returnedOrder = view.displayOrderToEdit(foundOrder, allStates, allProducts);

                //if there are no chnages
                if (foundOrder.equals(returnedOrder)) {

                    //display order and let them know there are no chnages and send them to main menu
                    view.displayEditOrderDetails(returnedOrder, orderDate);

                } else {
                    //there were changes
                    //calculate changes
                    Order updatedOrder = service.calculateCosts(returnedOrder);

                    //display changes
                    //ask if they want to update order
                    boolean orderToUpdate = view.displayOrderDetails(updatedOrder, orderDate, false);
                    //if yes then update order
                    if (orderToUpdate) {
                        Order order = service.editOrder(updatedOrder, orderNumber, orderDate);

                        view.displayEditOrderSucess(order.getOrderNumber(), orderDate);

                    } else {
                        //if no then go to main menu
                        view.displayGoToMainMenu();
                    }
                }
            } else {
                //if they dont want to edit go to main menu
                view.displayGoToMainMenu();
            }

        } else {
            // order is null to to main menu
            view.displayOrderNotFound();
        }

    }

    private void removeOrder() throws FlooringMasteryPersistenceException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringmasteryInvalidAreaException {

        //get date/day when order was placed
        String orderDate = view.getOrderDateNoRestriction();
        //get order number
        Integer orderNumber = view.getOrderNumber();
        //find order
        Order foundOrder = service.getOrder(orderDate, orderNumber);

        //if order not null
        if (foundOrder != null) {
            //display order and ask if they want to delete
            boolean deleteOrder = view.displayFoundOrder(foundOrder, orderDate, true);

            //if yes
            if (deleteOrder) {
                //remove order
                service.removeOrder(foundOrder, orderNumber, orderDate);
                //show success
                view.displayRemoveSuccess();
                //go to main menu
                view.displayGoToMainMenu();
            }

        } else {
            //order was null
            //display not found
            //go to main menu
            view.displayOrderNotFound();
        }

    }

    private void exportAllData() throws FlooringMasteryPersistenceException {
        //export data to file
        boolean exported = service.exportOrder();

        //if successful display sucess
        if (exported) {
            view.displayExportSuccess();
        } else {
            // display fail
            view.displayExportFail();
        }
        //go o menu
        view.displayGoToMainMenu();

    }

    private void unknownCommand() {
        view.displayUnkownCommand();
    }

    private void exitMessage() {
        view.displayExit();
    }

    private void showErrorMsg(String errorMsg) {
        view.displayErrorMessage(errorMsg);
    }

}
