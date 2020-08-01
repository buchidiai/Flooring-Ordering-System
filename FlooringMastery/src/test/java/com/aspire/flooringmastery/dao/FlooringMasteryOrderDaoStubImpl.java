/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Order;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryOrderDaoStubImpl implements FlooringMasteryOrderDao {

    public Order onlyOrder;

    public FlooringMasteryOrderDaoStubImpl() {

        this.onlyOrder = new Order(3);
        this.onlyOrder.setCustomerName("CVS");
        this.onlyOrder.setProductType("Tile");
        this.onlyOrder.setArea(new BigDecimal("100.00"));
        this.onlyOrder.setState("TX");
        this.onlyOrder.setTaxRate(new BigDecimal("4.70"));
        this.onlyOrder.setCostPerSquareFoot(new BigDecimal("3.50"));
        this.onlyOrder.setLaborCostPerSquareFoot(new BigDecimal("4.15"));
        this.onlyOrder.setMaterialCost(new BigDecimal("300.00"));
        this.onlyOrder.setLaborCost(new BigDecimal("10.00"));
        this.onlyOrder.setTax(new BigDecimal("150.00"));
        this.onlyOrder.setTotal(new BigDecimal("10000.00"));

    }

    public FlooringMasteryOrderDaoStubImpl(Order onlyOrder) {
        this.onlyOrder = onlyOrder;
    }

    @Override
    public Order addOrder(Order order, Integer orderNumber) throws FlooringMasteryPersistenceException {

        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;

        } else {
            return null;
        }
    }

    @Override
    public boolean removeOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    public boolean exportOrders() throws FlooringMasteryPersistenceException {
        return true;
    }

    @Override
    public Order getOrder(String orderDate, Integer orderNumber) throws FlooringMasteryPersistenceException {

        if (orderNumber.equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;

        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;

    }

    @Override
    public Integer getMaxOrderNumber(String date) throws FlooringMasteryPersistenceException {

        return onlyOrder.getOrderNumber();
    }

}
