/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.model;

import java.math.BigDecimal;

/**
 *
 * @author louie
 */
public class OrderDetail {

    private String customerName;
    private String state;
    private Product product;
    private BigDecimal area;

    public OrderDetail(String customerName, String state, Product product, BigDecimal area) {
        this.customerName = customerName;
        this.state = state;
        this.product = product;
        this.area = area;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getState() {
        return state;
    }

    public Product getProduct() {
        return product;
    }

    public BigDecimal getArea() {
        return area;
    }

}
