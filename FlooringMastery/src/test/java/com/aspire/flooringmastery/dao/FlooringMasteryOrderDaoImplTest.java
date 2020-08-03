/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.util.Util;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author louie
 */
public class FlooringMasteryOrderDaoImplTest {

    FlooringMasteryOrderDao testDao;

    Order orderToAdd1;
    Order orderToAdd2;
    Order orderToEdit;
    String currentDate = Util.getTodaysDate();
    String orderDate = currentDate;

    public FlooringMasteryOrderDaoImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        String testOrderFolder = "TestOrders/";
        String testOrderFile = "Order_";
        String backupFolder = "TestBackup/";
        String backupFile = "DataExport.txt";
        String fullPath = "TestOrders/Order_" + currentDate + ".txt";

        // Use the FileWriter to quickly blank the file
        //new FileWriter(fullPath);
        File file = new File(fullPath);
        if (file.exists()) {
            file.delete();

        }
        testDao = new FlooringMasteryOrderDaoImpl(testOrderFolder, testOrderFile, backupFolder, backupFile);

        //customer name
        String customerName = "JVC BANK";
        //state
        String state = "CA";
        //state tax percentage
        BigDecimal taxRate = new BigDecimal("25.00");
        //producttype of work to be done
        String productType = "Tile";
        //area
        BigDecimal area = new BigDecimal("300.00");
        //costPerSquareFoot
        BigDecimal costPerSquareFoot = new BigDecimal("3.50");
        //laborCostPerSquareFoot
        BigDecimal laborCostPerSquareFoot = new BigDecimal("4.15");
        //materialCost
        BigDecimal materialCost = new BigDecimal("1050.00");
        //laborCost
        BigDecimal laborCost = new BigDecimal("1245.00");
        //tax for work to be done
        BigDecimal taxForWork = new BigDecimal("573.75");
        //total
        BigDecimal total = new BigDecimal("2868.75");

        //order number 1
        orderToAdd1 = new Order(2, customerName, state, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxForWork, total);
        //order number 2
        orderToAdd2 = new Order(5, "Union Bank", "WA", new BigDecimal("13.00"), "Tile", new BigDecimal("140.00"), costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxForWork, total);
        //order number 3 / 1 ~> editted to be set to number 1
        orderToEdit = new Order(8, "Chase Bank", "TX", new BigDecimal("13.00"), "Wood", new BigDecimal("100.00"), costPerSquareFoot, laborCostPerSquareFoot, materialCost, laborCost, taxForWork, total);
    }

    //cant test order number because of implemetation
    //test all field when order is added
    @Test
    public void testAddOrderProductType() throws Exception {

        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert
        // Check the data is equal
        assertEquals(orderToAdd1.getProductType(),
                orderAdded.getProductType(),
                "Checking order type.");
    }

    @Test
    public void testAddOrderCustomerName() throws Exception {

        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getCustomerName(),
                orderAdded.getCustomerName(),
                "Checking customer name.");

    }

    @Test
    public void testAddOrderState() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getState(),
                orderAdded.getState(),
                "Checking state.");
    }

    @Test
    public void testAddOrderTaxRate() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getTaxRate(),
                orderAdded.getTaxRate(),
                "Checking taxRate.");
    }

    @Test
    public void testAddOrderArea() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getArea(),
                orderAdded.getArea(),
                "Checking area.");
    }

    @Test
    public void testAddOrderCostPerSquareFoot() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getCostPerSquareFoot(),
                orderAdded.getCostPerSquareFoot(),
                "Checking CostPerSquareFoot.");
    }

    @Test
    public void testAddOrderLaborCostPerSquareFoot() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getLaborCostPerSquareFoot(),
                orderAdded.getLaborCostPerSquareFoot(),
                "Checking LaborCostPerSquareFoot.");
    }

    @Test
    public void testAddOrderMaterialCost() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getMaterialCost(),
                orderAdded.getMaterialCost(),
                "Checking materialCost.");
    }

    @Test
    public void testAddOrderLaborCost() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getLaborCost(),
                orderAdded.getLaborCost(),
                "Checking laborCost.");
    }

    @Test
    public void testAddOrderTaxForWorkDone() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getTax(),
                orderAdded.getTax(),
                "Checking tax for work done.");
    }

    @Test
    public void testAddOrderTotal() throws Exception {
        Order orderAdded = testDao.addOrder(orderToAdd1, 1, orderDate);
        //  -assert

        // Check the data is equal
        assertEquals(orderToAdd1.getTotal(),
                orderAdded.getTotal(),
                "Checking total.");
    }

    //test edit for the 5 changes made
    @Test
    public void testEditOrderCustomerName() throws Exception {
        testDao.addOrder(orderToAdd1, 5, orderDate);
        Order orderEditted = testDao.editOrder(orderToEdit, 6, currentDate);

        //  -assert
        // Check the data is equal
        assertNotEquals(orderToAdd1.getCustomerName(),
                orderEditted.getCustomerName(),
                "Checking editted customer name.");
    }

    @Test
    public void testEditOrderState() throws Exception {
        testDao.addOrder(orderToAdd1, 5, orderDate);
        Order orderEditted = testDao.editOrder(orderToEdit, 6, currentDate);
        //  -assert

        // Check the data is equal
        assertNotEquals(orderToAdd1.getState(),
                orderEditted.getState(),
                "Checking editted state.");
    }

    @Test
    public void testEditOrderTaxRate() throws Exception {
        testDao.addOrder(orderToAdd1, 5, orderDate);
        Order orderEditted = testDao.editOrder(orderToEdit, 6, currentDate);
        //  -assert

        // Check the data is equal
        assertNotEquals(orderToAdd1.getTaxRate(),
                orderEditted.getTaxRate(),
                "Checking editted tax rate.");
    }

    @Test
    public void testEditOrderProductType() throws Exception {
        testDao.addOrder(orderToAdd1, 5, orderDate);
        Order orderEditted = testDao.editOrder(orderToEdit, 6, currentDate);
        //  -assert

        // Check the data is equal
        assertNotEquals(orderToAdd1.getProductType(),
                orderEditted.getProductType(),
                "Checking editted product type.");
    }

    @Test
    public void testEditOrderArea() throws Exception {
        testDao.addOrder(orderToAdd1, 5, orderDate);
        Order orderEditted = testDao.editOrder(orderToEdit, 6, currentDate);
        //  -assert

        // Check the data is equal
        assertNotEquals(orderToAdd1.getArea(),
                orderEditted.getArea(),
                "Checking editted area.");
    }

    //remove order
    @Test
    public void testRemoveOrder() throws Exception {
        Order order = testDao.addOrder(orderToAdd1, 1, orderDate);
        boolean orderRemoved = testDao.removeOrder(orderToAdd1, order.getOrderNumber(), currentDate);
        //  -assert
        // Check the data is equal
        assertTrue(orderRemoved,
                "Checking order is removed.");
    }

    //get order
    @Test
    public void testGetOrder() throws Exception {
        Order order = testDao.addOrder(orderToAdd1, 1, orderDate);

        Order found = testDao.getOrder(currentDate, 2);

        //  -assert
        // Check the data is equal
        assertTrue(order.equals(found),
                "Checking found order.");

    }

    //get all orders
    @Test
    public void testGetAllOrders() throws Exception {
        Order order1 = testDao.addOrder(orderToAdd1, 1, orderDate);
        Order order2 = testDao.addOrder(orderToAdd2, 2, orderDate);

        // Retrieve the list of all orders within the DAO
        List<Order> allorders = testDao.getAllOrders(Util.getTodaysDate());

        // First check the general contents of the list
        assertNotNull(allorders, "The list of orders must not null");
        assertEquals(2, allorders.size(), "List of orders should have 2 orders.");

        // Then the specifics
        assertTrue(testDao.getAllOrders(currentDate).contains(order1),
                "The list of orders should include JVC BANK.");
        assertTrue(testDao.getAllOrders(currentDate).contains(order2),
                "The list of orders should include Chase BANK.");
    }

}
