/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.service;

import com.aspire.flooringmastery.dao.FlooringMasteryAuditDao;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDao;
import com.aspire.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.aspire.flooringmastery.model.Order;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private final FlooringMasteryOrderDao orderDao;
    private final FlooringMasteryAuditDao auditDao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrderDao orderDao, FlooringMasteryAuditDao auditDao) {
        this.orderDao = orderDao;
        this.auditDao = auditDao;
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

        return orderDao.getAllOrders(orderDate);
    }

}
