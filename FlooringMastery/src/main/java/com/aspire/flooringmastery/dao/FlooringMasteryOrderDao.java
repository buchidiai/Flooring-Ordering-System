/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Order;
import java.util.List;

/**
 *
 * @author louie
 */
public interface FlooringMasteryOrderDao {

    Order addOrder(Order order) throws FlooringMasteryPersistenceException;

    Order editOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException;

    boolean removeOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException;

    boolean exportOrders() throws FlooringMasteryPersistenceException;

    Order getOrder(String orderDate, Integer orderNumber) throws FlooringMasteryPersistenceException;

    List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException;

}
