/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.service;

import com.aspire.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.Product;
import java.util.List;

/**
 *
 * @author louie
 */
public interface FlooringMasteryServiceLayer {

    Order addOrder(Order order, String orderDate) throws FlooringMasteryInvalidProductTypeException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringmasteryInvalidAreaException, FlooringMasteryPersistenceException;

    Order calculateCosts(Order order) throws FlooringMasteryInvalidProductTypeException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException;

    Order editOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryInvalidProductTypeException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException;

    boolean removeOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException;

    boolean exportOrder() throws FlooringMasteryPersistenceException;

    Order getOrder(String orderDate, Integer orderNumber) throws FlooringMasteryPersistenceException;

    List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException;

    List<Product> getAllProducts() throws FlooringMasteryPersistenceException;

    List<String> getAllStates() throws FlooringMasteryPersistenceException;

}
