/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.ui;

import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.OrderDetail;
import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.util.Util;
import java.math.BigDecimal;
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

    public String getOrderDateToDisplayAllOrders() {

        String orderDate = io.readDate("Please enter an Order Date: e.g 05/13/2020 - MM/DD/YYYY");
        return orderDate;
    }

    public String getOrderDate() {

        String orderDate = io.readDateForOrders("Please enter an Order Date: e.g 05/13/2020 - MM/DD/YYYY");
        return orderDate;
    }

    public void displayQuerying(String date, int size) {
        displaySpace();

        io.print("Querying orders for " + date);

        displayDots();

        io.print(size + " results were found");

        displaySpace();

        if (size == 0) {

            displayGoToMainMenu();

        } else {

            io.readString("Press Enter to view Orders.");
        }

    }

    private void displayReviewOrder() {

        io.print("*******************************");
        io.print("*******************************");
        io.print("********* Review Order ********");
        io.print("*******************************");

    }

    public boolean displayOrderDetails(OrderDetail orderDetail, String orderDate) {

        displayReviewOrder();

        io.print("_________________________________________________________________________________________________________________________________________________________");
        System.out.printf("|%13s|%18s|%5s|%10s|%10s|%10s|%12s|%12s|%10s|%13s|%10s|%10s| \n", "OrderDate", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");
        io.print("---------------------------------------------------------------------------------------------------------------------------------------------------------");

        //display orders
        displayOrders(orderDetail, orderDate);

        displaySpace();

        return askToPlaceOrder();

    }

    private void displayOrders(OrderDetail orderDetail, String orderDate) {

        //date of order
        String dateOfOrder = orderDate;

        //customer name
        String customerName = orderDetail.getCustomerName();
        //state
        String state = orderDetail.getState();
        //state tax percentage
        String taxRate = Util.addPercentage(orderDetail.getTaxRate());

        //product type
        String area = Util.BigDecimalToString(orderDetail.getArea());

        //area of work to be done
        String productType = orderDetail.getProductType();

        //costPerSquareFoot
        String costPerSquareFoot = Util.appendToMoney(orderDetail.getCostPerSquareFoot());
        //laborCostPerSquareFoot
        String laborCostPerSquareFoot = Util.appendToMoney(orderDetail.getLaborCostPerSquareFoot());
        //materialCost
        String materialCost = Util.appendToMoney(orderDetail.getMaterialCost());
        //laborCost
        String laborCost = Util.appendToMoney(orderDetail.getLaborCost());
        //tax for work to be done
        String taxForWork = Util.appendToMoney(orderDetail.getTax());
        //total
        String total = Util.appendToMoney(orderDetail.getTotal());

        //display products formatted
        System.out.printf("|%13s|%18s|%5s|%10s|%11s|%10s|%12s|%16s|%12s|%13s|%10s|%10s| \n", dateOfOrder, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxForWork, total);
        displaySpace();
        io.print("Your " + dateOfOrder + " order for " + productType + " with an area of " + area + " Sqft in " + state + " will come to a total of " + total);

    }

    private boolean askToPlaceOrder() {

        boolean placorder = io.readYesOrNo("Would you like to confirm this order? ");

        return placorder;
    }

    public OrderDetail getOrderDetails(List<Product> allProducts, List<String> allStates) {

        boolean validState = true;
        String state = "";

        //not valid
        //ask first name
//        String customerName = io.readString("Please enter Customer name: e.g - Acme, Inc.  ");
        String customerName = "test name";

        //validate state
        while (validState) {

            //ask state
            state = io.readString("Please enter State: e.g - TX  ").toUpperCase();

            if (!(allStates.contains(state))) {
                io.print("We are currently not taking orders in " + state);
                continue;
            }

            validState = false;

        }

        //ask for Product
        Product productType = getProductType(allProducts);
        // ask area
        //   BigDecimal area = io.readBigDecimal("Please enter an Area:  e.g 200");
        BigDecimal area = new BigDecimal("100.00");
        return new OrderDetail(customerName, productType.getProductType(), area, state);

    }

    public Product getProductType(List<Product> allProducts) {

        displayProducts(allProducts);

        int productIndex = io.readInt("Please select a product", 1, allProducts.size());

        return allProducts.get(productIndex - 1);

    }

    public void displayProducts(List<Product> products) {
        displayAvailableProducts();
        //display header for fields
        io.print("___________________________________________________");
        System.out.printf("|%5s|%13s|%10s|%8s| \n", "Choice", "ProductType", "CostPerSqFt", "LaborCostPerSqFt");
        io.print("---------------------------------------------------");
        //initiize product
        Product p;

        for (int i = 0; i < products.size(); i++) {
            //set product in array
            p = products.get(i);
            //display projecy
            displayProducts(i, p.getProductType(), p.getLaborCostPerSquareFoot(), p.getLaborCostPerSquareFoot());
        }
        displaySpace();

    }

    public void displayGoToMainMenu() {
        io.readString("Press Enter to go to Main Menu.");
    }

    private void displayAvailableProducts() {

        io.print("*******************************");
        io.print("*******************************");
        io.print("****** Available Products *****");
        io.print("*******************************");

    }

    public void displayOutOfProducts() {
        io.print("Sorry we are out of products at the moment try again");
        displaySpace();
        displayGoToMainMenu();

    }

    private void displayProducts(int index, String ProductType, BigDecimal CostPerSqFt, BigDecimal LaborCostPerSqFt) {

        //display products formatted
        System.out.printf("|%-6d|%13s|%11s|%16s|  \n", index + 1, ProductType, Util.appendToMoney(CostPerSqFt), Util.appendToMoney(LaborCostPerSqFt));
    }

    public void displayDots() {
        String dot = ".";

        for (int i = 0; i < 5; i++) {
            io.print(dot);

            if (i == 5) {
                dot += "\n";

            }

            dot += "..";

        }
    }

    public Integer getOrderNumber() {

        int orderNumber = io.readInt("Please enter an Order Number: e.g 32");
        return orderNumber;
    }

    public void displayAllOrders(List<Order> orders) {
        //display header for fields
        io.print("_______________________________________________________________________________________________________________________________________________________");
        System.out.printf("|%5s|%18s|%5s|%10s|%10s|%10s|%12s|%12s|%10s|%13s|%10s|%10s \n", "OrderNumber", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");
        io.print("-------------------------------------------------------------------------------------------------------------------------------------------------------");

        //initiize order
        Order o;

        if (orders.isEmpty()) {
            io.print("There are no Order To Display");

        } else {
            for (int i = 0; i < orders.size(); i++) {
                //set order in array
                o = orders.get(i);
                //display orders
                displayOrders(o);
            }
        }

        displaySpace();
        displayGoToMainMenu();
    }

    private void displayOrders(Order order) {

        Integer orderNumber = order.getOrderNumber();
        String customerName = order.getCustomerName();
        String state = order.getState();
        String taxRate = Util.addPercentage(order.getTaxRate());
        String productType = order.getProductType();
        String area = Util.BigDecimalToString(order.getArea());
        String costPerSquareFoot = Util.appendToMoney(order.getCostPerSquareFoot());
        String laborCostPerSquareFoot = Util.appendToMoney(order.getLaborCostPerSquareFoot());
        String materialCost = Util.appendToMoney(order.getMaterialCost());
        String laborCost = Util.appendToMoney(order.getLaborCost());
        String tax = Util.appendToMoney(order.getTax());
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
//        io.readString("Press Enter to continue");

        displayGoToMainMenu();
    }

    public void displayAllOrdersBanner() {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("*********** Display All Orders ************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayAddOrderBanner() {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("************** Add An order ***************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displaySpace() {
        io.print("");

    }

}
