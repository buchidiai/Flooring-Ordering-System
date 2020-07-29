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
import com.aspire.flooringmastery.dao.FlooringMasteryTaxDao;
import com.aspire.flooringmastery.model.Order;
import com.aspire.flooringmastery.model.OrderDetail;
import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private final FlooringMasteryOrderDao orderDao;
    private final FlooringMasteryProductDao productDao;
    private final FlooringMasteryTaxDao taxDao;

    private final FlooringMasteryAuditDao auditDao;

    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrderDao orderDao, FlooringMasteryProductDao productDao,
            FlooringMasteryTaxDao taxDao,
            FlooringMasteryAuditDao auditDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.auditDao = auditDao;
    }

    @Override
    public Order addOrder(OrderDetail orderDetail) throws FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException {

        String customerName = orderDetail.getCustomerName();

        System.out.println("customerName start " + customerName);

        String state = orderDetail.getState();
        System.out.println("state  start" + state);

        Product product = orderDetail.getProduct();

        System.out.println("product start " + product);

        BigDecimal area = orderDetail.getArea();

        System.out.println("area start" + area);

        validateCustomerName(customerName);

        validateState(state);

        validateArea(area);

        System.out.println("customerName end " + customerName);
        System.out.println("state  end" + state);
        System.out.println("product end " + product);
        System.out.println("area end" + area);

//        orderDao
        return null;

    }

    private void validateArea(BigDecimal area) throws FlooringmasteryInvalidAreaException, FlooringMasteryPersistenceException {
        System.out.println("3");
        if (area.compareTo(new BigDecimal("100.00")) < 0) {

            throw new FlooringmasteryInvalidAreaException("Area must be a minimum of 100");
        }

    }

    private void validateState(String state) throws FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException {
        System.out.println("2");
        List<Tax> taxes = taxDao.getAllTaxes();

        for (Tax t : taxes) {

            if (!(t.getStateAbbreviation().contains(state))) {
                throw new FlooringMasteryInvalidStateException("We are currently not taking orders for " + state);
            }

        }

    }

    private void validateCustomerName(String customerName) throws FlooringMasteryCustomerNameException {

        System.out.println("1");
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
