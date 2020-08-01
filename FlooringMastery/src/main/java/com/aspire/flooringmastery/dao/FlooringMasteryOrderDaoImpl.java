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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author louie
 */
public class FlooringMasteryOrderDaoImpl implements FlooringMasteryOrderDao {

    private final String ORDER_FOLDER;
    private final String BACKUP_FOLDER;
    private static Integer MAX_ORDER_NUMBER = 0;
    private final String ORDER_FILE_NAME;
    private final String BACKUP_FILE_NAME;
    private static final String DELIMITER = ",";
    private static final int NUMBER_OF_FIELDS = 12;
    private static final String HEADER = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";

    private List<Order> orders = new ArrayList<>();

    public FlooringMasteryOrderDaoImpl() {
        this.ORDER_FOLDER = "Orders/";
        this.ORDER_FILE_NAME = "Orders_";
        this.BACKUP_FOLDER = "Backup/";
        this.BACKUP_FILE_NAME = "DataExport.txt";
    }

    public FlooringMasteryOrderDaoImpl(String ORDER_FOLDER, String ORDER_FILE_NAME, String BACKUP_FOLDER, String BACKUP_FILE_NAME) {
        this.ORDER_FOLDER = ORDER_FOLDER;
        this.ORDER_FILE_NAME = ORDER_FILE_NAME;
        this.BACKUP_FOLDER = BACKUP_FOLDER;
        this.BACKUP_FILE_NAME = BACKUP_FILE_NAME;
    }

    @Override
    public Order addOrder(Order OrderDetail, Integer orderNumber) throws FlooringMasteryPersistenceException {

        //load orders of the day (not passed in date but actually day this order will be placed) and order will be added
        loadOrders(Util.getTodaysDate());

        //create new object with id max id
        //max id is found when orders are loaded from loadOrders()
        Order orderAdded = new Order((orderNumber + 1), OrderDetail);
        //add order to list of orders
        orders.add(orderAdded);
        //write orders in memory to file of order_day.txt
        writeOrder();

        //return order
        return orderAdded;
    }

    @Override
    public Order editOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {

        loadOrders(orderDate);

        Order orderFound = null;

        for (Order o : orders) {

            //find order with matching order number and return it
            if (Objects.equals(o.getOrderNumber(), orderNumber)) {
                o.setCustomerName(order.getCustomerName());
                o.setProductType(order.getProductType());
                o.setArea(order.getArea());
                o.setState(order.getState());
                o.setTaxRate(order.getTaxRate());
                o.setCostPerSquareFoot(order.getCostPerSquareFoot());
                o.setLaborCostPerSquareFoot(order.getLaborCostPerSquareFoot());
                o.setMaterialCost(order.getMaterialCost());
                o.setLaborCost(order.getLaborCost());
                o.setTax(order.getTax());
                o.setTotal(order.getTotal());
                orderFound = o;
                writeOrder(orderDate);
                break;
            }
        }
        return orderFound;
    }

    @Override
    public boolean removeOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
        loadOrders(orderDate);
        boolean removedOrder = false;
        for (Order o : orders) {
            if (Objects.equals(o.getOrderNumber(), orderNumber)) {
                orders.remove(o);
                removedOrder = true;
                writeOrder(orderDate);
                break;
            }
        }
        //return removed order
        return removedOrder;
    }

    @Override
    public boolean exportOrders() throws FlooringMasteryPersistenceException {
        return exportAllOrders();
    }

    @Override
    public Order getOrder(String orderDate, Integer orderNumber) throws FlooringMasteryPersistenceException {
        //load orders of the day of the order that will be edited

        loadOrders(orderDate);

        Order orderFound = null;

        for (Order o : orders) {
            //find order with matching order number and return it
            if (Objects.equals(o.getOrderNumber(), orderNumber)) {
                orderFound = o;
                break;
            }
        }
        //return found order
        return orderFound;
    }

    @Override
    public List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException {
        //load order by date to memory

        loadOrders(orderDate);

        return orders;
    }

    @Override
    public Integer getMaxOrderNumber(String orderDate) throws FlooringMasteryPersistenceException {

        Scanner scanner = null;

        Integer currentMax = 0;

        Integer finalMax = 0;
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

                    } catch (FileNotFoundException e) {

                        throw new FlooringMasteryPersistenceException(
                                "-_- Could not load order data into memory.", e);
                    }

                    //break out loop to get file
                    break;
                }
            }

            System.out.println("scanner " + scanner == null);

            //if file is not null
            if (scanner != null) {
                // currentLine holds the most recent line read from the file
                String currentLine;

                //skip first line for header is empty
                if (file.length() != 0) {
                    scanner.nextLine();
                }

                System.out.println("scanner.hasNextLine() " + scanner.hasNextLine());

                while (scanner.hasNextLine()) {

                    // get the next line in the file
                    currentLine = scanner.nextLine();

                    String[] orderTokens = currentLine.split(DELIMITER);

                    if (orderTokens.length == NUMBER_OF_FIELDS) {
                        //on load up size is zero so read from file
                        if (orders.isEmpty()) {

                            //create order object
                            currentMax = getMaxOrderNumber(orderTokens);
                            if (currentMax > finalMax) {
                                finalMax = currentMax;
                            }
                        } else {
                            //size is not zero
                            //check for duplicates for list
                            for (Order p : orders) {
                                //if order number in list  == order number in file
                                if (p.getOrderNumber() == (Integer.parseInt(orderTokens[0]))) {
                                    finalMax = p.getOrderNumber();
                                    if (p.getOrderNumber() > MAX_ORDER_NUMBER) {
                                        finalMax = MAX_ORDER_NUMBER;
                                    }
                                } else {
                                    currentMax = getMaxOrderNumber(orderTokens);
                                    if (currentMax > finalMax) {
                                        finalMax = currentMax + 1;
                                    }
                                }
                            }
                        }
                    }
                }
                // close scanner
                scanner.close();
            }

        }
        return finalMax;
    }

    private Integer getMaxOrderNumber(String[] orderTokens) {
        int count = 0;
        int max = 0;
        try {
            //get order number
            Integer orderNumber = Integer.parseInt(orderTokens[0]);

            if (orderNumber > max) {
                max = orderNumber;
            }
        } catch (Exception e) {
            count++;
            if (count <= 1) {
                System.out.println("");
                System.out.println("Unable to load order #" + orderTokens[0] + ", order number was not utilized");
            }
        }

        return max;

    }

    private boolean exportAllOrders() throws FlooringMasteryPersistenceException {
        //init scanner
        Scanner scanner = null;
        //init list of orders
        List<String> orderAsList = new ArrayList<>();
        //init printout
        PrintWriter out = null;

        //file object
        File ordersDirectory = new File(ORDER_FOLDER);
        File backupDirectory = new File(BACKUP_FOLDER);
        File backupFile = new File(BACKUP_FOLDER + BACKUP_FILE_NAME);

        //check if directory exists
        if (isExistingDir(ordersDirectory) && isExistingDir(backupDirectory)) {

            //it exists lets check if there are any files
            File[] dir_contents = ordersDirectory.listFiles();

            //get file in directory
            for (File f : dir_contents) {

                //get file date and ext
                String fileDate = f.getName().split("_")[1].split("\\.")[0];
                //format file date
                fileDate = fileDate.substring(0, 2) + "-" + fileDate.substring(2, 4) + "-" + fileDate.substring(4, fileDate.length());

                try {
                    //write to backup
                    out = new PrintWriter(new FileWriter(backupFile));
                    // Create Scanner for reading the file
                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(f)));

                } catch (IOException e) {

                    throw new FlooringMasteryPersistenceException(
                            "-_- Could not load order data into memory.", e);
                }
                //if scanner not null
                if (scanner != null) {
                    //init ordersas text
                    String orderAsText = "";
                    //check if file has next line
                    while (scanner.hasNextLine()) {
                        // get the next line in the file
                        orderAsText = scanner.nextLine();
                        //if line doesnt start with orderNumber
                        if (!(orderAsText.startsWith("OrderNumber"))) {
                            //if the line lenth is greater than 1
                            if (orderAsText.length() > 1) {
                                //append  & add to arraylist
                                orderAsText += "," + fileDate;
                                orderAsList.add(orderAsText);
                            }
                        }
                    }

                }
            }
            // close scanner
            scanner.close();
            //write header
            out.println(HEADER + ",OrderDate");
            //sort list
            Collections.sort(orderAsList);
            // loop through list & write to file
            for (String s : orderAsList) {
                out.println(s);

            }
            out.flush();

        } else {
            //directory doesnt exist ~> create and add to file it
            System.out.println("dir doesnt exist");

        }
        return true;
    }

    private void writeOrder(String date) throws FlooringMasteryPersistenceException {
        //   System.out.println("ok write order to file from memory");
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
//        System.out.println("ok write order to file from memory");
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
//        System.out.println("ok Write to file from Memory");
        PrintWriter out;
        String fileLocation = ORDER_FOLDER + ORDER_FILE_NAME + orderDay;

//        Scanner scanner = null;
        try {

            out = new PrintWriter(new FileWriter(fileLocation));

//            scanner = new Scanner(
//                    new BufferedReader(
//                            new FileReader(fileLocation)));
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
        File file = new File(ORDER_FOLDER + ORDER_FILE_NAME + orderDay);

        if (file.length() == 0) {
            out.println(HEADER);
        }

        for (Order currentOrder : orders) {

            // turn a Order into a String
            orderAsText = marshallOrder(currentOrder);

            out.println(orderAsText);
            out.flush();

        }
        // Clean up
        out.close();
    }

    private String marshallOrder(Order aOrder) {
        // We need to turn a Order object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // chips::2.50::10
//        System.out.println("marshallOrder ~> write to file " + aOrder.getCustomerName() + " , " + aOrder.getOrderNumber());
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
        Scanner scanner = null;
        boolean foundOrdersForDate = false;
        //order date + .txt extension

        String orderDateFileExt = Util.cleanDate(orderDate) + ".txt";

        //clear memory so when writeOrders() is called dont write existing data in memory from another day
        orders.removeAll(orders);

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

            if (orderNumber > MAX_ORDER_NUMBER) {
                MAX_ORDER_NUMBER = orderNumber;
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
