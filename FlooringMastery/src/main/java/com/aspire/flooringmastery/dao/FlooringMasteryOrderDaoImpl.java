/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.util.Util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author louie
 */
public class FlooringMasteryOrderDaoImpl implements FlooringMasteryOrderDao {

    private final String ORDER_FOLDER;
    private static Integer MAX_ORDER_NUMBER = 0;
    private final String ORDER_FILE_NAME;
    private static final String DELIMITER = ",";
    public static final int NUMBER_OF_FIELDS = 12;

    private List<Order> orders = new ArrayList<>();

    public FlooringMasteryOrderDaoImpl() {
        this.ORDER_FOLDER = "Orders/";
        this.ORDER_FILE_NAME = "Orders_";
    }

    public FlooringMasteryOrderDaoImpl(String ORDER_FOLDER, String ORDER_FILE_NAME) {
        this.ORDER_FOLDER = ORDER_FOLDER;
        this.ORDER_FILE_NAME = ORDER_FILE_NAME;
    }

    @Override
    public Order addOrder(Order OrderDetail) throws FlooringMasteryPersistenceException {

        loadOrders(Util.getTodaysDate());
        Order orderAdded = new Order((MAX_ORDER_NUMBER), OrderDetail);
        orders.add(orderAdded);
        writeOrder();
        return orderAdded;
    }

    @Override
    public Order editOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {

        loadOrders(orderDate);

        Order originalOrder = getOrder(orderDate, orderNumber);

        if (originalOrder != null) {
            originalOrder.setCustomerName(order.getCustomerName());
            originalOrder.setProductType(order.getProductType());
            originalOrder.setArea(order.getArea());
            originalOrder.setState(order.getState());
            originalOrder.setTaxRate(order.getTaxRate());
            originalOrder.setCostPerSquareFoot(order.getCostPerSquareFoot());
            originalOrder.setLaborCostPerSquareFoot(order.getLaborCostPerSquareFoot());
            originalOrder.setMaterialCost(order.getMaterialCost());
            originalOrder.setLaborCost(order.getLaborCost());
            originalOrder.setTax(order.getTax());
            originalOrder.setTotal(order.getTotal());
            writeOrder(orderDate);
        }

        return originalOrder;
    }

    @Override
    public boolean removeOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {

        loadOrders(orderDate);
        boolean removedOrder = false;

        Order originalOrder = getOrder(orderDate, orderNumber);
        if (originalOrder != null) {
            if (orders.remove(order)) {
                removedOrder = true;
                writeOrder(orderDate);

            }

        }

        return removedOrder;
    }

    @Override
    public void exportOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order getOrder(String orderDate, Integer orderNumber) throws FlooringMasteryPersistenceException {
        loadOrders(orderDate);

        Order orderFound = null;

        for (Order o : orders) {
            if (o.getOrderNumber() == orderNumber) {
                orderFound = o;
                break;
            }
        }
        return orderFound;
    }

    @Override
    public List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException {
        loadOrders(orderDate);

        return orders;
    }

    private void writeOrder(String date) throws FlooringMasteryPersistenceException {
        System.out.println("ok write order to file from memory");
        // date + .txt extension
        String orderDate = Util.cleanDate(date) + ".txt";

        //file object
        File dir = new File(ORDER_FOLDER);

        //check if directory exists
        if (isExistingDir(dir)) {
            //orders_file exists so add to it
            writeOrderObject(orderDate);
        } else {
            //directory doesnt exist ~> create and add to file it
            if (createDir(dir)) {
                writeOrderObject(orderDate);
            }

        }
    }

    private void writeOrder() throws FlooringMasteryPersistenceException {
        System.out.println("ok write order to file from memory");
        // date + .txt extension
        String orderDate = Util.getTodaysDate() + ".txt";

        //file object
        File dir = new File(ORDER_FOLDER);

        //check if directory exists
        if (isExistingDir(dir)) {
            //orders_file exists so add to it
            writeOrderObject(orderDate);
        } else {
            //directory doesnt exist ~> create and add to file it
            if (createDir(dir)) {
                writeOrderObject(orderDate);
            }

        }
    }

    public void writeOrderObject(String orderDay) throws FlooringMasteryPersistenceException {
        System.out.println("ok Write to file from Memory");
        PrintWriter out;
        String fileLocation = ORDER_FOLDER + ORDER_FILE_NAME + orderDay;

        Scanner scanner = null;
        try {
            out = new PrintWriter(new FileWriter(fileLocation));

            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileLocation)));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not write order data.", e);
        }

        // Write out the Order objects to the order file.
        // NOTE TO THE APPRENTICES: We could just grab the order map,
        // get the Collection of Orders and iterate over them but we've
        // already created a method that gets a List of Orders so
        // we'll reuse it.
        String orderAsText;

        //add header to file
        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";

        File file = new File(ORDER_FOLDER + ORDER_FILE_NAME + orderDay);

        if (file.length() == 0) {
            out.println(header);
        }

        for (Order currentOrder : orders) {

            // turn a Order into a String
            orderAsText = marshallOrder(currentOrder);

            out.println(orderAsText);
            out.flush();

            // Clean up
            out.close();

        }





    private String marshallOrder(Order aOrder) {
        // We need to turn a Order object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // chips::2.50::10
        System.out.println("marshallOrder ~> write to file " + aOrder.getCustomerName() + " , " + aOrder.getOrderNumber());
        // It's not a complicated process. Just get out each property,
        // and concatenate with our DELIMITER as a kind of spacer.
        // Start with the order id, since that's supposed to be first.

        if (aOrder.getOrderNumber() > MAX_ORDER_NUMBER) {

            MAX_ORDER_NUMBER = aOrder.getOrderNumber() + 1;

        }

        //order number
        String orderAsText = aOrder.getOrderNumber() + DELIMITER;

        // add the rest of the properties in the correct order:
        // price
        orderAsText += aOrder.getCustomerName() + DELIMITER;

        // price
        orderAsText += aOrder.getState() + DELIMITER;

        // price
        orderAsText += aOrder.getTaxRate() + DELIMITER;

        // price
        orderAsText += aOrder.getProductType() + DELIMITER;

        // price
        orderAsText += aOrder.getArea() + DELIMITER;

        // price
        orderAsText += aOrder.getCostPerSquareFoot() + DELIMITER;

        // quantity
        orderAsText += aOrder.getLaborCostPerSquareFoot() + DELIMITER;

        // quantity
        orderAsText += aOrder.getMaterialCost() + DELIMITER;

        // quantity
        orderAsText += aOrder.getLaborCost() + DELIMITER;

        orderAsText += aOrder.getTax() + DELIMITER;

        orderAsText += aOrder.getTotal();

        // We have now turned a order to text! Return it!
        return orderAsText;
    }

    // load order from text file
    private void loadOrders(String orderDate) throws FlooringMasteryPersistenceException {
        System.out.println("ok Load-orders into memory from file ");
        Scanner scanner = null;
        boolean foundOrdersForDate = false;
        //order date + .txt extension

        String orderDateFileExt = Util.cleanDate(orderDate) + ".txt";

        //file object
        File dir = new File(ORDER_FOLDER);
        File file = new File(ORDER_FOLDER + ORDER_FILE_NAME + orderDateFileExt);

        //check if directory exists
        if (isExistingDir(dir)) {
            //it exists lets check if there are any files
            File[] dir_contents = dir.listFiles();

            //get file in directory
            for (File f : dir_contents) {
                //get file date and ext
                String fileDate = f.getName().split("_")[1];

                //check if file date and order date are the same
                if (fileDate.equals(orderDateFileExt)) {

                    try {
                        // Create Scanner for reading the file
                        scanner = new Scanner(
                                new BufferedReader(
                                        new FileReader(f)));
                        foundOrdersForDate = true;
                    } catch (FileNotFoundException e) {

                        throw new FlooringMasteryPersistenceException(
                                "-_- Could not load order data into memory.", e);
                    }

                    //break out loop to get file
                    break;
                }
            }
            if (foundOrdersForDate == false) {
                //clear orders out if not found for that day
                orders.removeAll(orders);
            } else {
                //if file is not null
                if (scanner != null) {
                    // currentLine holds the most recent line read from the file
                    String currentLine;
                    // currentOrder holds the most recent order unmarshalled
                    Order currentOrder = null;
                    // Go through ORDER_FILE line by line, decoding each line into a
                    // Order object by calling the unmarshallOrder method.
                    // Process while we have more lines in the file

                    //skip first line
                    if (file.length() != 0) {
                        scanner.nextLine();
                    }

                    while (scanner.hasNextLine()) {

                        // get the next line in the file
                        currentLine = scanner.nextLine();

                        // unmarshall the line into a Order
                        currentOrder = unmarshallOrder(currentLine);

//                    System.out.println("currentOrder " + currentOrder);
                        //Put currentOrder into the map using order id as the key
                        if (currentOrder != null && !(orders.contains(currentOrder))) {

                            orders.add(currentOrder);

                        }
                    }

                }
                // close scanner
                scanner.close();
            }

        }

    }

    private Order unmarshallOrder(String orderAsText) {

        // orderAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // 1,Ada Lovelace,CA,25.00,Tile,249.00,3.50,4.15,871.50,1033.35,476.21,2381.06
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in orderTokens.
        // Which should look like this:
        String[] orderTokens = orderAsText.split(DELIMITER);

        //OrderNumber[0] = 1
        //CustomerName[1] = Ada Lovelace
        //State[2] = CA
        //TaxRate[3] = 25.00
        //OrderType[4] = Tile
        //Area[5] = 249.00
        //CostPerSquareFoot[6] = 3.50
        //LaborCostPerSquareFoot[7] = 4.15
        //MaterialCost[8] = 871.50
        //LaborCost[9] = 1033.35
        //Tax[10] = 476.21
        //Total[11] = 2381.06
        Order orderFromFile = null;

        if (orderTokens.length == NUMBER_OF_FIELDS) {
            //on load up size is zero so read from file
            if (orders.isEmpty()) {
                //create order object
                orderFromFile = createOrderObject(orderTokens);
            } else {
                //size is not zero
                //check for duplicates for list
                for (Order p : orders) {
                    //if order number in list  == order number in file
                    if (p.getOrderNumber() == (Integer.parseInt(orderTokens[0]))) {

                        //no need to add it
                        return null;
                    } else {

                        orderFromFile = createOrderObject(orderTokens);
                    }
                }
            }
        } else {
            return null;
        }
        // We have now created a order! Return it!
        return orderFromFile;
    }

    private Order createOrderObject(String[] orderTokens) {
        int count = 0;
        Order orderFromFile = null;

        try {
            //create order object
            Integer orderNumber = Integer.parseInt(orderTokens[0]);

            if (orderNumber >= MAX_ORDER_NUMBER) {
                MAX_ORDER_NUMBER = orderNumber + 1;
            }
            String customerName = orderTokens[1];
            String state = orderTokens[2];
            BigDecimal taxRate = new BigDecimal(orderTokens[3]);
            String orderType = orderTokens[4];
            BigDecimal area = new BigDecimal(orderTokens[5]);
            BigDecimal costPerSquareFoot = new BigDecimal(orderTokens[6]);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(orderTokens[7]);
            BigDecimal materialCost = new BigDecimal(orderTokens[8]);
            BigDecimal laborCost = new BigDecimal(orderTokens[9]);
            BigDecimal tax = new BigDecimal(orderTokens[10]);
            BigDecimal total = new BigDecimal(orderTokens[11]);

            orderFromFile = new Order(orderNumber, customerName, state, taxRate, orderType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
        } catch (Exception e) {

            count++;

            if (count <= 1) {
                System.out.println("");
                System.out.println("Unable to load order #" + orderTokens[0] + ", it has been removed from inventory");
            }

        }

        return orderFromFile;

    }

    private boolean createDir(File dir) {
        return dir.mkdir();
    }

    private boolean isExistingDir(File dir) {

        return dir.exists();
    }

}
