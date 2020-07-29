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
    private static final String DELIMITER = ",";
    public static final int NUMBER_OF_FIELDS = 12;

    private List<Order> orders = new ArrayList<>();

    public FlooringMasteryOrderDaoImpl() {
        this.ORDER_FOLDER = "Orders/";

    }

    public FlooringMasteryOrderDaoImpl(String ORDER_FOLDER) {
        this.ORDER_FOLDER = ORDER_FOLDER;
    }

    @Override
    public Order addOrder(Order orderDetail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order editOrder(Integer OrderNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeOrder(Integer OrderNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order getOrder(String OrderNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException {
        loadOrders(orderDate);

        return orders;
    }

    private void loadOrders(String orderDate) throws FlooringMasteryPersistenceException {
        Scanner scanner = null;
        //order date + .txt extension
        String orderDateFileExt = Util.cleanDate(orderDate) + ".txt";
        //file object
        File dir = new File(ORDER_FOLDER);

        //check if directory exists
        if (isExistingDir(dir)) {
            //it exists lets check if there are any files
            File[] dir_contents = dir.listFiles();

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
                }
                //break out loop to get file
                break;
            }

            if (scanner != null) {

                // currentLine holds the most recent line read from the file
                String currentLine;
                // currentOrder holds the most recent order unmarshalled
                Order currentOrder = null;
                // Go through ORDER_FILE line by line, decoding each line into a
                // Order object by calling the unmarshallOrder method.
                // Process while we have more lines in the file

                //skip first line
                scanner.nextLine();
                while (scanner.hasNextLine()) {
                    // get the next line in the file
                    currentLine = scanner.nextLine();

                    // unmarshall the line into a Order
                    currentOrder = unmarshallOrder(currentLine);
                    //Put currentOrder into the map using order id as the key
                    if (currentOrder != null) {

                        orders.add(currentOrder);
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
        //ProductType[4] = Tile
        //Area[5] = 249.00
        //CostPerSquareFoot[6] = 3.50
        //LaborCostPerSquareFoot[7] = 4.15
        //MaterialCost[8] = 871.50
        //LaborCost[9] = 1033.35
        //Tax[10] = 476.21
        //Total[11] = 2381.06
        Order orderFromFile = null;

        String orderName = "";

        if (orderTokens.length == NUMBER_OF_FIELDS) {

            //on load up size is zero so read from file
            if (orders.size() == 0) {
                //create order object
                orderFromFile = createOrderObject(orderTokens);
            } else {
                //size is not zero
                //check for duplicates for list
                for (Order p : orders) {
                    //if order number in list  == order number in file
                    if (p.getOrderNumber().equals(Integer.parseInt(orderTokens[0]))) {
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
            String customerName = orderTokens[1];
            String state = orderTokens[2];
            BigDecimal taxRate = new BigDecimal(orderTokens[3]);
            String productType = orderTokens[4];
            BigDecimal area = new BigDecimal(orderTokens[5]);
            BigDecimal costPerSquareFoot = new BigDecimal(orderTokens[6]);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(orderTokens[7]);
            BigDecimal materialCost = new BigDecimal(orderTokens[8]);
            BigDecimal laborCost = new BigDecimal(orderTokens[9]);
            BigDecimal tax = new BigDecimal(orderTokens[10]);
            BigDecimal total = new BigDecimal(orderTokens[11]);

            orderFromFile = new Order(orderNumber, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, tax, total);
        } catch (Exception e) {

            count++;

            if (count <= 1) {
                System.out.println("");
                System.out.println("Unable to load order #" + orderTokens[0] + ", it has been removed from inventory");
            }

        }

        return orderFromFile;

    }

    private boolean checkIfDirectoryExistsAndCreate(File dir) {

        boolean isCreated = false;

        //--Orders/ doesnt exist so create it
        if (!dir.exists()) {
            boolean dirCreated = dir.mkdir();
            if (dirCreated) {
                System.out.println("Dir created");
                isCreated = true;
            } else {
                System.out.println("Something went wrong creating");
                isCreated = false;
            }

        } else {
            //return true if it exists
            isCreated = true;
        }
        return isCreated;
    }

    private boolean createDir(File dir) {
        return dir.mkdir();
    }

    private boolean isExistingDir(File dir) {

        return dir.exists();
    }

}
