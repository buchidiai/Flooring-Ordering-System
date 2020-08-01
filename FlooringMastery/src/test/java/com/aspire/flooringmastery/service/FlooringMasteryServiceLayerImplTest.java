/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.service;

import com.aspire.flooringmastery.dao.FlooringMasteryAuditDao;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDao;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDaoStubImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.aspire.flooringmastery.dao.FlooringMasteryProductDao;
import com.aspire.flooringmastery.dao.FlooringMasteryProductDaoStubImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryTaxDao;
import com.aspire.flooringmastery.dao.FlooringMasteryTaxDaoStubImpl;
import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.util.Util;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author louie
 */
public class FlooringMasteryServiceLayerImplTest {

    private FlooringMasteryServiceLayer service;
    private Order order = null;
    String currentDate = Util.getTodaysDate();

    public FlooringMasteryServiceLayerImplTest() {

        // Instantiate the Audit DAO
        FlooringMasteryAuditDao myAudit = new FlooringMasteryAuditDaoStubImpl();
        //Declare a FlooringMasteryOrderDao variable and initialize it with a FlooringMasteryOrderDaoStubImpl reference.
        FlooringMasteryOrderDao myOrderDao = new FlooringMasteryOrderDaoStubImpl();
        //Declare a FlooringMasteryProductDao variable and initialize it with a FlooringMasteryProductDaoStubImpl reference.
        FlooringMasteryProductDao myProductDao = new FlooringMasteryProductDaoStubImpl();
        //Declare a FlooringMasteryTaxDao variable and initialize it with a FlooringMasteryTaxDaoStubImpl reference.
        FlooringMasteryTaxDao myTaxDao = new FlooringMasteryTaxDaoStubImpl();

        service = new FlooringMasteryServiceLayerImpl(myOrderDao, myProductDao, myTaxDao, myAudit);
    }

    @BeforeEach
    public void setUp() {
        //customer name
        String customerName = "Test Bank";
        //state
        String state = "TX";
        //producttype of work to be done
        String productType = "Tile";
        //area
        BigDecimal area = new BigDecimal("300.00");

        order = new Order(customerName, productType, area, state);

    }

    @Test
    public void testGetAllProducts() throws Exception {

        List<Product> allProducts = service.getAllProducts();

        // ACT & ASSERT
        assertEquals(1, allProducts.size(),
                "Should only have one product.");
    }

    @Test
    public void testGetAllStates() throws Exception {

        List<String> allState = service.getAllStates();

        // ACT & ASSERT
        assertEquals(1, allState.size(),
                "Should only have one state.");
    }

    @Test
    public void testGetOrder() throws Exception {//goes throug stub
        // ARRANGE
        Order orderClone = new Order(3);
        orderClone.setCustomerName("CVS");
        orderClone.setProductType("Tile");
        orderClone.setArea(new BigDecimal("100.00"));
        orderClone.setState("TX");
        orderClone.setTaxRate(new BigDecimal("4.70"));
        orderClone.setCostPerSquareFoot(new BigDecimal("3.50"));
        orderClone.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        orderClone.setMaterialCost(new BigDecimal("300.00"));
        orderClone.setLaborCost(new BigDecimal("10.00"));
        orderClone.setTax(new BigDecimal("150.00"));
        orderClone.setTotal(new BigDecimal("10000.00"));

        // ACT & ASSERT
        Order order = service.getOrder(currentDate, 3);

        assertNotNull(order, "Getting 3 should be not null.");
        assertEquals(orderClone, order,
                "Order stored under 0001 should be CVS.");

        Order shouldBeNull = service.getOrder(currentDate, 2);
        assertNull(shouldBeNull, "Getting order number 2 should be null.");

    }

    @Test
    public void testEditOrder() throws Exception {
        // ARRANGE
        Order orderClone = new Order(3);
        orderClone.setCustomerName("CVS");
        orderClone.setProductType("Tile");
        orderClone.setArea(new BigDecimal("100.00"));
        orderClone.setState("TX");
        orderClone.setTaxRate(new BigDecimal("4.70"));
        orderClone.setCostPerSquareFoot(new BigDecimal("3.50"));
        orderClone.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        orderClone.setMaterialCost(new BigDecimal("300.00"));
        orderClone.setLaborCost(new BigDecimal("10.00"));
        orderClone.setTax(new BigDecimal("150.00"));
        orderClone.setTotal(new BigDecimal("10000.00"));

        // ACT & ASSERT
        Order order = service.editOrder(orderClone, 3, currentDate);

        assertNotNull(order, "Getting 3 should be not null.");
        assertEquals(orderClone, order,
                "Order stored under 0001 should be CVS.");

        Order shouldBeNull = service.getOrder(currentDate, 1);
        assertNull(shouldBeNull, "Getting order number 1 should be null.");

    }

    @Test
    public void testRemoveOrder() throws Exception {
        // ARRANGE
        Order orderClone = new Order(3);
        orderClone.setCustomerName("CVS");
        orderClone.setProductType("Tile");
        orderClone.setArea(new BigDecimal("100.00"));
        orderClone.setState("TX");
        orderClone.setTaxRate(new BigDecimal("4.70"));
        orderClone.setCostPerSquareFoot(new BigDecimal("3.50"));
        orderClone.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        orderClone.setMaterialCost(new BigDecimal("300.00"));
        orderClone.setLaborCost(new BigDecimal("10.00"));
        orderClone.setTax(new BigDecimal("150.00"));
        orderClone.setTotal(new BigDecimal("10000.00"));

        // ACT & ASSERT
        boolean removedOrder = service.removeOrder(orderClone, 3, currentDate);

        assertTrue(removedOrder, "order removed should be order 3.");

    }

    @Test
    public void testGetAllOrders() throws Exception {
        // ARRANGE
        Order newOrder = new Order(3);
        newOrder.setCustomerName("CVS");
        newOrder.setProductType("Tile");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setState("TX");
        newOrder.setTaxRate(new BigDecimal("4.70"));
        newOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        newOrder.setMaterialCost(new BigDecimal("300.00"));
        newOrder.setLaborCost(new BigDecimal("10.00"));
        newOrder.setTax(new BigDecimal("150.00"));
        newOrder.setTotal(new BigDecimal("10000.00"));

        service.addOrder(newOrder);

        List<Order> allOrders = service.getAllOrders("07312020");

        // ACT & ASSERT
        assertEquals(1, allOrders.size(),
                "Should only have one product.");

        assertTrue(service.getAllOrders("07312020").contains(newOrder),
                "The one customer should be CVS.");
    }

    @Test
    public void testAddOrder() {
        // ARRANGE
        Order newOrder = new Order(3);
        newOrder.setCustomerName("CVS");
        newOrder.setProductType("Tile");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setState("TX");
        newOrder.setTaxRate(new BigDecimal("4.70"));
        newOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        newOrder.setMaterialCost(new BigDecimal("300.00"));
        newOrder.setLaborCost(new BigDecimal("10.00"));
        newOrder.setTax(new BigDecimal("150.00"));
        newOrder.setTotal(new BigDecimal("10000.00"));
        try {
            service.addOrder(newOrder);

        } catch (FlooringMasteryInvalidProductTypeException | FlooringMasteryCustomerNameException | FlooringMasteryInvalidStateException | FlooringMasteryPersistenceException | FlooringmasteryInvalidAreaException e) {
            // ASSERT
            fail("Order was valid. No exception should have been thrown.");

        }

    }

    @Test
    public void testBlankCustomerName() {
        // ARRANGE
        Order newOrder = new Order(3);
        newOrder.setCustomerName("");//~>
        newOrder.setProductType("Tile");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setState("TX");
        newOrder.setTaxRate(new BigDecimal("4.70"));
        newOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        newOrder.setMaterialCost(new BigDecimal("300.00"));
        newOrder.setLaborCost(new BigDecimal("10.00"));
        newOrder.setTax(new BigDecimal("150.00"));
        newOrder.setTotal(new BigDecimal("10000.00"));
        try {
            service.addOrder(newOrder);
            fail("Expected cutomer name exception.");
        } catch (FlooringMasteryInvalidProductTypeException | FlooringMasteryInvalidStateException | FlooringMasteryPersistenceException | FlooringmasteryInvalidAreaException e) {
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryCustomerNameException e) {
            return;
        }

    }

    @Test
    public void testAllowedCustomerName() {
        // ARRANGE
        Order newOrder = new Order(3);
        newOrder.setCustomerName("Mike, Inc. !@#$%^&*(");//~>
        newOrder.setProductType("Tile");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setState("TX");
        newOrder.setTaxRate(new BigDecimal("4.70"));
        newOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        newOrder.setMaterialCost(new BigDecimal("300.00"));
        newOrder.setLaborCost(new BigDecimal("10.00"));
        newOrder.setTax(new BigDecimal("150.00"));
        newOrder.setTotal(new BigDecimal("10000.00"));
        try {
            service.addOrder(newOrder);
            fail("Expected cutomer name exception.");
        } catch (FlooringMasteryInvalidProductTypeException | FlooringMasteryInvalidStateException | FlooringMasteryPersistenceException | FlooringmasteryInvalidAreaException e) {
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryCustomerNameException e) {
            return;
        }

    }

    @Test
    public void testInvalidState() {
        // ARRANGE
        Order newOrder = new Order(3);
        newOrder.setCustomerName("CVS");
        newOrder.setProductType("Tile");
        newOrder.setArea(new BigDecimal("100.00"));
        newOrder.setState("Dubai"); //~>
        newOrder.setTaxRate(new BigDecimal("4.70"));
        newOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        newOrder.setMaterialCost(new BigDecimal("300.00"));
        newOrder.setLaborCost(new BigDecimal("10.00"));
        newOrder.setTax(new BigDecimal("150.00"));
        newOrder.setTotal(new BigDecimal("10000.00"));

        try {

            service.addOrder(newOrder);
            fail("Expected invalid state exception.");
        } catch (FlooringMasteryInvalidProductTypeException | FlooringMasteryCustomerNameException | FlooringMasteryPersistenceException | FlooringmasteryInvalidAreaException e) {
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryInvalidStateException e) {
            return;
        }

    }

    @Test
    public void testInvalidArea() {

        // ARRANGE
        Order newOrder = new Order(3);
        newOrder.setCustomerName("CVS");
        newOrder.setProductType("Tile");
        newOrder.setArea(new BigDecimal("30.00"));//~>
        newOrder.setState("TX");
        newOrder.setTaxRate(new BigDecimal("4.70"));
        newOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        newOrder.setMaterialCost(new BigDecimal("300.00"));
        newOrder.setLaborCost(new BigDecimal("10.00"));
        newOrder.setTax(new BigDecimal("150.00"));
        newOrder.setTotal(new BigDecimal("10000.00"));

        try {

            service.addOrder(newOrder);
            fail("Expected invalid area exception");
        } catch (FlooringMasteryInvalidProductTypeException | FlooringMasteryCustomerNameException | FlooringMasteryInvalidStateException | FlooringMasteryPersistenceException e) {
            fail("Incorrect exception was thrown.");
        } catch (FlooringmasteryInvalidAreaException e) {
            return;
        }

    }

    @Test
    public void testInvalidProductType() {

        // ARRANGE
        Order newOrder = new Order(3);
        newOrder.setCustomerName("CVS");
        newOrder.setProductType("puppy");//~>
        newOrder.setArea(new BigDecimal("200.00"));
        newOrder.setState("TX");
        newOrder.setTaxRate(new BigDecimal("4.70"));
        newOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        newOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        newOrder.setMaterialCost(new BigDecimal("300.00"));
        newOrder.setLaborCost(new BigDecimal("10.00"));
        newOrder.setTax(new BigDecimal("150.00"));
        newOrder.setTotal(new BigDecimal("10000.00"));
        try {

            service.addOrder(newOrder);
            fail("Expected invalid Product type exception");
        } catch (FlooringMasteryCustomerNameException | FlooringMasteryInvalidStateException | FlooringMasteryPersistenceException | FlooringmasteryInvalidAreaException e) {
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryInvalidProductTypeException e) {
            return;
        }

    }

    @Test
    public void testCalculcationsLaborCost() throws Exception {
        // ARRANGE
        Order customerorder = service.calculateCosts(order);

        BigDecimal expectedLaborCost = new BigDecimal("1245.00");

        // ACT & ASSERT
        assertEquals(customerorder.getLaborCost(), expectedLaborCost,
                "matercost should be correct.");

    }

    @Test
    public void testCalculcationsTaxes() throws Exception {
        // ARRANGE
        Order customerorder = service.calculateCosts(order);

        BigDecimal expectedtaxes = new BigDecimal("102.13");

        // ACT & ASSERT
        assertEquals(customerorder.getTax(), expectedtaxes,
                "taxes should be correct.");

    }

    @Test
    public void testCalculcationsTotal() throws Exception {
        // ARRANGE
        Order customerorder = service.calculateCosts(order);

        BigDecimal expectedTotal = new BigDecimal("2397.13");

        // ACT & ASSERT
        assertEquals(customerorder.getTotal(), expectedTotal,
                "total should be correct.");

    }
}
