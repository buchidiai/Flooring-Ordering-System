/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.service;

import com.aspire.flooringmastery.dao.FlooringMasteryAuditDao;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDao;
import com.aspire.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.aspire.flooringmastery.dao.FlooringMasteryProductDao;
import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.OrderDetail;
import com.aspire.flooringmastery.model.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private final FlooringMasteryOrderDao orderDao;
    private final FlooringMasteryProductDao productDao;
    private final FlooringMasteryAuditDao auditDao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrderDao orderDao, FlooringMasteryProductDao productDao,
            FlooringMasteryAuditDao auditDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.auditDao = auditDao;
    }

    @Override
    public Order addOrder(OrderDetail orderDetail) throws FlooringMasteryCustomerNameException {

        String customerName = orderDetail.getCustomerName();

        String state = orderDetail.getCustomerName();

        Product product = orderDetail.getProduct();

        BigDecimal area = orderDetail.getArea();

        boolean isValid = true;

        validateCustomerName(customerName);

        return null;

    }

    private void validateState(String state) throws FlooringMasteryCustomerNameException {

    }

    private void validateCustomerName(String customerName) throws FlooringMasteryCustomerNameException {
//        Pattern p = Pattern.compile("/^[a-zA-Z0-9,.!? ]*$/", Pattern.CASE_INSENSITIVE);
//
//        Matcher m = p.matcher(customerName);
//
//        boolean b = m.find();

        if (customerName.isEmpty()) {

            throw new FlooringMasteryCustomerNameException("Customer name can't be empty");

        }
//
//        if (b) {
//
//            throw new FlooringMasteryCustomerNameException("Customer name contains can only contain [0-9][a-z][.][,]");
//
//        }

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

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {

        return productDao.getAllProducts();
    }

}
