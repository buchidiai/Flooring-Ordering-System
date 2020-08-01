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
import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.model.Tax;
import com.aspire.flooringmastery.util.Util;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author louie
 */
@Component
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private final FlooringMasteryOrderDao orderDao;
    private final FlooringMasteryProductDao productDao;
    private final FlooringMasteryTaxDao taxDao;

    private final FlooringMasteryAuditDao auditDao;

    @Autowired
    public FlooringMasteryServiceLayerImpl(FlooringMasteryOrderDao orderDao, FlooringMasteryProductDao productDao,
            FlooringMasteryTaxDao taxDao,
            FlooringMasteryAuditDao auditDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.auditDao = auditDao;
    }

    @Override
    public Order addOrder(Order order) throws FlooringMasteryInvalidProductTypeException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException {

        Integer orderNumber = orderDao.getMaxOrderNumber(Util.getTodaysDate());

        //validate customer name
        validateCustomerName(order.getCustomerName());

        //validate state
        validateState(order.getState());

        //validate area
        validateArea(order.getArea());
        //validate product
        validateProductType(order.getProductType());

        Order orderToAdd = orderDao.addOrder(order, orderNumber);
        auditDao.writeAuditEntry(orderToAdd, "Add Order");
        return orderToAdd;

    }

    @Override
    public Order calculateCosts(Order order) throws FlooringMasteryInvalidProductTypeException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException {

        //customer name entered
        String customerName = order.getCustomerName();

        //state entered
        String state = order.getState();

        //product selected
        String product = order.getProductType();

        //area entered
        BigDecimal area = order.getArea();

        //validate customer name
        validateCustomerName(customerName);

        //validate state
        validateState(state);

        //validate area
        validateArea(area);

        //validate product
        validateProductType(order.getProductType());

        //get specific tax object
        Tax taxForState = taxDao.getTax(state);

        //gget specific product object
        Product productForSale = productDao.getProduct(product);

        //do calculations
        BigDecimal materialCost = (area.multiply(productForSale.getCostPerSquareFoot())).setScale(2, RoundingMode.CEILING);
        BigDecimal laborCost = (area.multiply(productForSale.getLaborCostPerSquareFoot())).setScale(2, RoundingMode.CEILING);
        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxForState.getTaxRate().divide(new BigDecimal("100.00"))).setScale(2, RoundingMode.CEILING);
        BigDecimal total = (materialCost.add(laborCost.add(tax)));

        order.setCostPerSquareFoot(productForSale.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(productForSale.getLaborCostPerSquareFoot());
        order.setTaxRate(taxForState.getTaxRate());
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);

        return order;
    }

    @Override
    public Order editOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryInvalidProductTypeException, FlooringMasteryCustomerNameException, FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException, FlooringmasteryInvalidAreaException {

        //validate customer name
        validateCustomerName(order.getCustomerName());

        //validate state
        validateState(order.getState());

        //validate area
        validateArea(order.getArea());

        //validate product
        validateProductType(order.getProductType());

        Order OrderToEdit = orderDao.editOrder(order, orderNumber, orderDate);

        auditDao.writeAuditEntry(order, "Edit Order");

        return OrderToEdit;

    }

    @Override
    public boolean removeOrder(Order order, Integer orderNumber, String orderDate) throws FlooringMasteryPersistenceException {

        boolean orderToRemove = orderDao.removeOrder(order, orderNumber, orderDate);
        auditDao.writeAuditEntry(order, "Remove Order");
        return orderToRemove;
    }

    @Override
    public Order getOrder(String orderDate, Integer orderNumber) throws FlooringMasteryPersistenceException {

        return orderDao.getOrder(orderDate, orderNumber);
    }

    @Override
    public List<Order> getAllOrders(String orderDate) throws FlooringMasteryPersistenceException {

        return orderDao.getAllOrders(orderDate);
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {

        return productDao.getAllProducts();
    }

    @Override
    public List<String> getAllStates() throws FlooringMasteryPersistenceException {

        return taxDao.getAllStates();
    }

    @Override
    public boolean exportOrder() throws FlooringMasteryPersistenceException {
        return orderDao.exportOrders();
    }

    private void validateArea(BigDecimal area) throws FlooringmasteryInvalidAreaException, FlooringMasteryPersistenceException {

        if (area.compareTo(new BigDecimal("100.00")) < 0) {

            throw new FlooringmasteryInvalidAreaException("Area must be a minimum of 100.");
        }

    }

    private void validateState(String state) throws FlooringMasteryInvalidStateException, FlooringMasteryPersistenceException {

        Tax tax = taxDao.getTax(state);

        if (tax == null) {
            throw new FlooringMasteryInvalidStateException("We are currently not taking orders in " + state + ".");
        }
    }

    private void validateProductType(String productType) throws FlooringMasteryInvalidProductTypeException, FlooringMasteryPersistenceException {

        Product product = productDao.getProduct(productType);

        if (product == null) {
            throw new FlooringMasteryInvalidProductTypeException("We do not offer  " + productType + " as a product.");
        }
    }

    private void validateCustomerName(String customerName) throws FlooringMasteryCustomerNameException {
        if (customerName.isEmpty()) {
            throw new FlooringMasteryCustomerNameException("Customer name can't be empty.");
        }
        if (!(customerName.matches("[A-Za-z0-9,. ]+"))) {
            throw new FlooringMasteryCustomerNameException("Customer name can only have Alphanumeric value including comma and period.");
        }
    }

}
