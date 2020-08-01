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

        System.out.println("FlooringMasteryOrderDaoStubImpl  instiated");

    }

    public FlooringMasteryOrderDaoStubImpl(Order onlyOrder) {
        this.onlyOrder = onlyOrder;
    }

    @Override
    public Order addOrder(Order order, Integer orderNumber) throws FlooringMasteryPersistenceException {
        System.out.println("add order in OrderDaoStubImpl " + order.getCustomerName());
        System.out.println("add order in OrderDaoStubImpl " + order.getOrderNumber());
        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;

        } else {
            return null;
        }
    }

    @Override
    public Order editOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
        System.out.println("edit order in OrderDaoStubImpl " + order.getCustomerName());
        System.out.println("edit order in OrderDaoStubImpl " + order.getOrderNumber());
        if (order.getOrderNumber().equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;

        } else {
            return null;
        }
    }

    @Override
    public boolean removeOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {
        System.out.println("remove order in OrderDaoStubImpl " + order.getCustomerName());
        System.out.println("remove order in OrderDaoStubImpl " + order.getOrderNumber());

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

        System.out.println("get order in OrderDaoStubImpl " + orderNumber);
        System.out.println("get order in OrderDaoStubImpl " + orderDate);
        if (orderNumber.equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;

        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException {

        System.out.println("get all order in OrderDaoStubImpl " + orderDate);

        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;

    }

    @Override
    public Integer getMaxorderNumber(String date) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
