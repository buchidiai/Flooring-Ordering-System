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

    Order addOrder(Order orderDetail);

    Order editOrder(Order OrderNumber);

    boolean removeOrder(Integer OrderNumber);

    void exportOrder();

    Order getOrder(String OrderNumber);

    List<Order> getAllOrders(String OrderNumber);

}
