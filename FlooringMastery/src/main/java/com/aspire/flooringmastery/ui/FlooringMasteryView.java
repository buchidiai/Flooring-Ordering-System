/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.ui;

import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.util.Util;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryView {

    UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {

        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("*  <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Quit");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        int option = io.readInt("Please pick an option: ", 1, 6);

        return option;

    }

    public String getOrderDate() {

        String orderDate = io.readDate("Please enter an Order Date: e.g 05/13/2020 - MM/DD/YYYY");
        return orderDate;
    }

    public void displayQuerying(String date, int size) {
        displaySpace();

        io.print("Querying orders for " + date);

        io.print(size + " results were found");

        displaySpace();

        if (size == 0) {

            io.readString("Press Enter to go to Main Menu.");

        } else {

            io.readString("Press Enter to view Orders.");
        }

    }

    public Integer getOrderNumber() {

        int orderNumber = io.readInt("Please enter an Order Number: e.g 32");
        return orderNumber;
    }

    public void displayAllOrder(List<Order> orders) {
        //display header for fields
        io.print("_______________________________________________________________________________________________________________________________________________________");
        System.out.printf("|%5s|%18s|%5s|%10s|%10s|%10s|%12s|%12s|%10s|%13s|%10s|%10s \n", "OrderNumber", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");
        io.print("-------------------------------------------------------------------------------------------------------------------------------------------------------");

        //initiize product
        Order o;

        if (orders.isEmpty()) {
            io.print("There are no Order To Display");

        } else {
            for (int i = 0; i < orders.size(); i++) {
                //set product in array
                o = orders.get(i);
                //display projecy
                displayOrders(o);
            }
        }

        displaySpace();
    }

    public void displayOrders(Order order) {

        Integer orderNumber = order.getOrderNumber();
        String customerName = order.getCustomerName();
        String state = order.getState();
        String taxRate = Util.appendToMoney(order.getTaxRate());
        String productType = order.getProductType();
        String area = Util.BigDecimalToString(order.getArea());
        String costPerSquareFoot = Util.BigDecimalToString(order.getCostPerSquareFoot());
        String laborCostPerSquareFoot = Util.BigDecimalToString(order.getLaborCostPerSquareFoot());
        String materialCost = Util.appendToMoney(order.getMaterialCost());
        String laborCost = Util.appendToMoney(order.getLaborCost());
        String tax = Util.BigDecimalToString(order.getTax());
        String total = Util.appendToMoney(order.getTotal());

        //display products formatted
        System.out.printf("|%11d|%18s|%5s|%10s|%11s|%10s|%12s|%16s|%12s|%13s|%10s|%10s \n", orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot, materialCost, laborCostPerSquareFoot, laborCost, tax, total);

    }

    public void displayErrorMessage(String errorMsg) {

        io.print("*******************************************");
        io.print("***************** ERROR *****************");
        io.print("      *******************************      ");
        io.print("** " + errorMsg + " **");
        io.print("      *******************************      ");
        io.readString("Press Enter to continue");
    }

    public void displayAllOrdersBanner() {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("*********** Display All Orders ************");
        displaySpace();

    }

    public void displaySpace() {
        io.print("");

    }

}
