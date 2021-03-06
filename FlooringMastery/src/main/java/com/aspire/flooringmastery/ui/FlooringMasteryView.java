/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.ui;

import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.util.TableGenerator;
import com.aspire.flooringmastery.util.Util;
import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author louie
 */
@Component
public class FlooringMasteryView {

    UserIO io;
    private TableGenerator tblg = new TableGenerator();

    @Autowired
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

    private List<String> createHeader(String col1, String col2, String col3, String col4, String col5, String col6, String col7, String col8,
            String col9, String col10, String col11, String col12) {
        List<String> headersListA = new ArrayList<>();

        if ((headersListA.isEmpty())) {
            headersListA.add(col1);
            headersListA.add(col2);
            headersListA.add(col3);
            headersListA.add(col4);
            headersListA.add(col5);
            headersListA.add(col6);
            headersListA.add(col7);
            headersListA.add(col8);
            headersListA.add(col9);
            headersListA.add(col10);
            headersListA.add(col11);
            headersListA.add(col12);
        }

        return headersListA;

    }

    public boolean displayFoundOrder(Order order, String orderDate, boolean askToDelete) {

        displayFoundOrder();

        List<String> header = createHeader("OrderDate", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");
        //display order
        displayOneOrder(order, orderDate, header);

        displaySpace();

        return askToDelete ? askToDeleteOrder() : askToEditOrder();

    }

    private void displayOneOrder(Order order, String orderDate, List<String> header) {

        //date of order
        String dateOfOrder = orderDate;
        //customer name
        String customerName = order.getCustomerName();
        //state
        String state = order.getState();
        //state tax percentage
        String taxRate = Util.addPercentage(order.getTaxRate());
        //product type
        String area = Util.BigDecimalToString(order.getArea());
        //area of work to be done
        String productType = order.getProductType();
        //costPerSquareFoot
        String costPerSquareFoot = Util.appendToMoney(order.getCostPerSquareFoot());
        //laborCostPerSquareFoot
        String laborCostPerSquareFoot = Util.appendToMoney(order.getLaborCostPerSquareFoot());
        //materialCost
        String materialCost = Util.appendToMoney(order.getMaterialCost());
        //laborCost
        String laborCost = Util.appendToMoney(order.getLaborCost());
        //tax for work to be done
        String taxForWork = Util.appendToMoney(order.getTax());
        //total
        String total = Util.appendToMoney(order.getTotal());

        createRow(header, dateOfOrder, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxForWork, total);

        io.print("Your order for " + productType + " with an area of " + area + " Sqft in " + state + " totaling in " + total + " was found.");
    }

    public void createRow(List<String> header, String orderIdentifier, String customerName, String state, String taxRate, String productType,
            String area,
            String costPerSquareFoot, String laborCostPerSquareFoot, String materialCost, String laborCost, String taxForWork,
            String total) {
        List<List<String>> rowsList = new ArrayList<>();

        List<String> row = new ArrayList<>();

        row.add(orderIdentifier);
        row.add(customerName);
        row.add(state);
        row.add(taxRate);
        row.add(productType);
        row.add(area);
        row.add(costPerSquareFoot);
        row.add(laborCostPerSquareFoot);
        row.add(materialCost);
        row.add(laborCost);
        row.add(taxForWork);
        row.add(total);

        rowsList.add(row);
        io.print(tblg.generateTable(header, rowsList));

        rowsList.removeAll(rowsList);
        row.removeAll(row);

    }

    private boolean askToEditOrder() {

        boolean placOrder = io.readYesOrNo("Would you like to Edit this order? (Yes/No) ");

        return placOrder;
    }

    private boolean askToDeleteOrder() {

        boolean deleteOrder = io.readYesOrNo("Would you like to Delete this order? (Yes/No) ");

        return deleteOrder;
    }

    public String getOrderDateNoRestriction() {

        String orderDate = io.readDate("Please enter an Order Date: e.g 05/13/2020 - MM/DD/YYYY");
        return orderDate;
    }

    public String getOrderDateRestricted() {

        String orderDate = io.readDateForOrders("Please enter an Order Date: e.g 05/13/2020 - MM/DD/YYYY");
        return orderDate;
    }

    public String getOrderDateToEdit() {

        String orderDate = io.readDateForOrders("Please enter an Order Date: e.g 05/13/2020 - MM/DD/YYYY");
        return orderDate;
    }

    public void displayQuerying(String date, int size) {
        displaySpace();

        io.print("Querying orders for " + date);

        displayDots();

        io.print(size + " results were found.");

        displaySpace();

        if (size == 0) {

            displayGoToMainMenu();

        } else {

            io.readString("Press Enter to view Orders.");
        }

    }

    public Order displayOrderToEdit(Order order, List<String> allStates, List<Product> allProducts) {

        //
        //ask user for new info
        //
        String customerName = getValidCustomerName(order.getCustomerName());

        //validate new state
        String state = getValidState(order.getState(), allStates);
        //ask for Product and validate
        //will return null if user hits enter key else will return product type
        Product productType = getValidProductType(allProducts, order.getProductType());
        //validate area
        BigDecimal area = getValidArea(order.getArea());

        //check if all values are empty  and return original order
        if (customerName.isEmpty() && state.isEmpty() && (productType == null) && (area == null)) {
            return order;

        } else {

            String newCustomerName = customerName.isEmpty() ? order.getCustomerName() : customerName;

            String newProductType = productType == null ? order.getProductType() : productType.getProductType();

            String newState = state.isEmpty() ? order.getState() : state;

            BigDecimal newArea = (area == null) ? order.getArea() : area;

            return new Order(newCustomerName, newProductType, newArea, newState);

        }

    }

    private String getValidCustomerName(String orderCustomerName) {
        String customerName = "";
        while (true) {

            customerName = io.readString("Please enter new Customer name: (" + orderCustomerName + ") ");

            if (customerName.isEmpty()) {
                break;
            } else if (!(customerName.matches("[A-Za-z0-9,. ]+"))) {
                io.print("Customer name can only have Alphanumeric value including comma and period.");
                continue;
            }

            break;
        }
        return customerName;
    }

    private BigDecimal getValidArea(BigDecimal orderArea) {
        boolean validArea = true;
        BigDecimal area = new BigDecimal("0");
        String areaString = "";

        while (validArea) {
            try {
                areaString = io.readString("Please enter an Area: (" + orderArea + ")  e.g 200");
                if (!areaString.isEmpty()) {
                    area = new BigDecimal(areaString);
                    if (area.compareTo(new BigDecimal("100.00")) < 0) {
                        io.print("Area must be a minimum of 100.");
                        continue;
                    }
                } else {
                    return null;
                }

                validArea = false;

            } catch (Exception e) {
                io.print("Area must be a valid number.");
                continue;
            }

        }

        return area;
    }

    private String getValidState(String orderState, List<String> allStates) {
        boolean validState = true;
        String state = "";

        while (validState) {
            //ask state
            state = io.readString("Please enter a new State: (" + orderState + ")  ").toUpperCase();
            //check if state is not empty
            //so validate input
            if (!state.isEmpty()) {
                if (!(allStates.contains(state))) {
                    io.print("We are currently not taking orders in " + state + ".");
                    continue;
                }
            }
            validState = false;
        }

        return state;
    }

    public Product getValidProductType(List<Product> allProducts, String previousProduct) {

        displayProducts(allProducts);

        boolean isValid = true;

        int productIndex = 0;

        while (isValid) {

            try {
                String userAnswer = io.readString("Please select a product: (" + previousProduct + ")  (" + 1 + "-" + allProducts.size() + ")  ");

                if (userAnswer.isEmpty()) {
                    return null;
                } else if (parseInt(userAnswer) >= 1 && parseInt(userAnswer) <= allProducts.size()) {

                    productIndex = parseInt(userAnswer);

                }

                if (productIndex == 0) {
                    io.print("Choice must be from (" + 1 + "-" + allProducts.size() + ")");

                    continue;
                }
                isValid = false;

            } catch (Exception e) {

                io.print("Choice must be from (" + 1 + "-" + allProducts.size() + ")");

                continue;

            }

        }

        return allProducts.get(productIndex - 1);

    }

    public boolean displayOrderDetails(Order orderDetail, String orderDate, boolean newOrder) {

        displayReviewOrder();

        List<String> header = createHeader("OrderDate", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");

//        io.print("_______________________________________________________________________________________________________________________________________________________________");
//        System.out.printf("|%13s|%18s|%5s|%10s|%17s|%16s|%12s|%12s|%10s|%13s|%10s|%10s| \n", "OrderDate", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");
//        io.print("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
        //display orders
        displayOrders(orderDetail, orderDate, header);

        displaySpace();

        return newOrder ? askToPlaceOrder() : askToUpdateOrder();

    }

    public void displayEditOrderDetails(Order orderDetail, String orderDate) {

        displayReviewOrder();

        List<String> header = createHeader("OrderDate", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");

        //display orders
        displayOrders(orderDetail, orderDate, header);

        displaySpace();

        displayGoToMainMenuAfterNoChanges();

    }

    private void displayOrders(Order orderDetail, String orderDate, List<String> header) {

        //date of order
        String dateOfOrder = orderDate;

        //customer name
        String customerName = Util.capitalizeFirstWord(orderDetail.getCustomerName());
        //state
        String state = orderDetail.getState();
        //state tax percentage
        String taxRate = Util.addPercentage(orderDetail.getTaxRate());

        //product type
        String area = Util.BigDecimalToString(orderDetail.getArea()) + " Sqft";

        //area of work to be done
        String productType = Util.capitalizeFirstWord(orderDetail.getProductType());

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

        createRow(header, dateOfOrder, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxForWork, total);

        displaySpace();
        io.print("Your " + dateOfOrder + " order for " + productType + " with an area of " + area + " Sqft in " + state + " will come to a total of " + total);

    }

    private boolean askToPlaceOrder() {

        boolean placOrder = io.readYesOrNo("Would you like to confirm this order? (Yes/No) ");

        return placOrder;
    }

    private boolean askToUpdateOrder() {

        boolean updateOrder = io.readYesOrNo("Would you like to update this order? (Yes/No) ");

        return updateOrder;
    }

    public void displayGoToMainMenuAfterNoChanges() {
        io.print("There were no changes in your order.");
        io.readString("Please press Enter to go to main Menu.");
    }

    public Order getOrderDetails(List<Product> allProducts, List<String> allStates) {
        String state = "";

        //ask first name
        String customerName = "";
        while (true) {

            customerName = io.readString("Please enter Customer name: e.g - Walmart, Inc");

            if (!(customerName.matches("[A-Za-z0-9,. ]+"))) {

                io.print("Customer name can only have Alphanumeric value including comma and period.");
                continue;

            }
            break;
        }

        while (customerName.isEmpty()) {
            io.print("Customer name cant be blank.");
            continue;
        }
        //validate state
        while (true) {
            //ask state
            state = io.readString("Please enter State: e.g - TX , NY, CA  ").toUpperCase();
            if (state.isEmpty()) {
                io.print("State cant be blank.");
                continue;
            }
            if (!(allStates.contains(state))) {
                io.print("We are currently not taking orders in " + state + ".");
                continue;
            }
            break;
        }
        //ask for Product
        Product productType = getProductType(allProducts);
        // ask area
        BigDecimal area = new BigDecimal("0.00");
        while (true) {
            area = io.readBigDecimal("Please enter an Area:  e.g 200");
            if (area.compareTo(new BigDecimal("100.00")) < 0) {
                io.print("Area must be a minimum of 100.");
                continue;
            }
            break;
        }
        return new Order(Util.capitalizeFirstWord(customerName), productType.getProductType(), area, state);
    }

    public Product getProductType(List<Product> allProducts) {

        displayProducts(allProducts);

        int productIndex = io.readInt("Please select a product:  ", 1, allProducts.size());

        return allProducts.get(productIndex - 1);

    }

    public void displayProducts(List<Product> products) {
        displayAvailableProducts();
        //display header for fields
        io.print("_______________________________________________________________");
        System.out.printf("|%5s|%25s|%10s|%8s| \n", "Choice", "ProductType", "CostPerSqFt", "LaborCostPerSqFt");
        io.print("---------------------------------------------------------------");
        //initiize product
        Product p;

        for (int i = 0; i < products.size(); i++) {
            //set product in array
            p = products.get(i);
            //display projecy
            displayProducts(i, p.getProductType(), p.getLaborCostPerSquareFoot(), p.getLaborCostPerSquareFoot());
            io.print("_______________________________________________________________");
        }
        displaySpace();
    }

    public void displayGoToMainMenu() {
        io.readString("Press Enter to go to Main Menu.");
    }

    public void displayOutOfProducts() {
        io.print("Sorry we are out of products at the moment.");
        displaySpace();
        displayGoToMainMenu();
    }

    private void displayProducts(int index, String ProductType, BigDecimal CostPerSqFt, BigDecimal LaborCostPerSqFt) {
        //display products formatted
        System.out.printf("|%-6d|%25s|%11s|%16s|  \n", index + 1, ProductType, Util.appendToMoney(CostPerSqFt), Util.appendToMoney(LaborCostPerSqFt));
    }

    public void displayDots() {

        io.print(".\n" + "..\n"
                + ".....\n"
                + "........\n"
                + "...........\n"
                + "..............");
    }

    public Integer getOrderNumber() {

        int orderNumber = io.readInt("Please enter an Order Number: e.g 32");
        return orderNumber;
    }

    public void displayAllOrders(List<Order> orders, String day) {

        displayDayOrders(day);
        //display header for fields

        io.print("_________________________________________________________________________________________________________________________________________________________________________________________________________");
        System.out.printf("| %5s|%25s|%5s|%10s|%27s|%18s|%12s|%12s|%10s|%17s|%17s|%17s| \n", "OrderNumber", "CustomerName", "State", "TaxRate", "ProductType", "Area", "CostPerSqft", "LaborCostPerSqft", "MaterialCost", "LaborCost", "Tax", "Total");
        io.print("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        //initiize order
        Order o;

        if (orders.isEmpty()) {
            io.print("There are no Order To Display.");

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
        String area = Util.BigDecimalToString(order.getArea()) + " Sqft";
        String costPerSquareFoot = Util.appendToMoney(order.getCostPerSquareFoot());
        String laborCostPerSquareFoot = Util.appendToMoney(order.getLaborCostPerSquareFoot());
        String materialCost = Util.appendToMoney(order.getMaterialCost());
        String laborCost = Util.appendToMoney(order.getLaborCost());
        String tax = Util.appendToMoney(order.getTax());
        String total = Util.appendToMoney(order.getTotal());

        //display products formatted
        System.out.printf("| %11d|%25s|%5s|%10s|%27s|%18s|%12s|%16s|%12s|%17s|%17s|%17s| \n", orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot, materialCost, laborCostPerSquareFoot, laborCost, tax, total);
        io.print("_________________________________________________________________________________________________________________________________________________________________________________________________________");
    }

    public void displayErrorMessage(String errorMsg) {

        io.print("*******************************************");
        io.print("***************** ERROR *****************");
        io.print("      *******************************      ");
        io.print("** " + errorMsg + " **");
        io.print("      *******************************      ");
        displayGoToMainMenu();
    }

    private void displayReviewOrder() {

        io.print("*******************************************");
        io.print("*******************************************");
        io.print("*************** Review Order **************");
        io.print("*******************************************");

    }

    private void displayFoundOrder() {

        io.print("*******************************************");
        io.print("*******************************************");
        io.print("*************** Found Order ***************");
        io.print("*******************************************");

    }

    public void displayOrderNotFound() {

        io.print("*******************************************");
        io.print("*******************************************");
        io.print("************* Order Not Found *************");
        io.print("*******************************************");

        displayGoToMainMenu();

    }

    public void displayAllOrdersBanner() {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("*********** Display All Orders ************");
        io.print("*******************************************");
        displaySpace();
    }

    private void displayAvailableProducts() {

        io.print("*******************************************");
        io.print("*******************************************");
        io.print("************ Available Products ***********");
        io.print("*******************************************");

    }

    public void displayAddOrderBanner() {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("************** Add An Order ***************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayAddOrderSucess(Integer orderNumber, String orderDueDate) {
        displayDots();
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("********* Add Order Was Successful ********");
        displayEditOrderBanner(orderNumber, orderDueDate);
        displaySpace();
        displayGoToMainMenu();

    }

    public void displayEditOrderBanner(Integer orderNumber, String orderDueDate) {
        io.print("******** Service Date: " + orderDueDate + " *********");
        io.print("                 Order #" + orderNumber + "                 ");
        io.print("*******************************************");

    }

    public void displayEditOrderBanner() {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("************** Edit An Order **************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayEditOrderSucess(Integer orderNumber, String orderDueDate) {
        displayDots();
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("********* Edit Order Was Successful *******");
        displayEditOrderBanner(orderNumber, orderDueDate);
        displaySpace();

    }

    public void displayRemoveOrderBanner() {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("************* Remove An Order *************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayRemoveSuccess() {
        displayDots();
        io.print("*******************************************");
        io.print("******** Remove Order Was Successful ******");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayExportSuccess() {
        displayDots();
        io.print("*******************************************");
        io.print("*********** Export Was Successful *********");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayExportFail() {
        displayDots();
        io.print("*******************************************");
        io.print("*************** Export Failed *************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayDayOrders(String day) {
        io.print("*******************************************");
        io.print("*******************************************");
        io.print("************ " + day + " Orders ************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displaySpace() {
        io.print("");

    }

    public void displayUnkownCommand() {
        io.print("*******************************************");
        io.print("************ Unknown - Command ************");
        io.print("*******************************************");
        displaySpace();

    }

    public void displayExit() {
        io.print("*******************************************");
        io.print("***************** Good-Bye ****************");
        io.print("*******************************************");

    }

}
