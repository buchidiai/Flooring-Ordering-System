/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Order;

/**
 *
 * @author louie
 */
public interface FlooringMasteryAuditDao {

    public void writeAuditEntry(Order order, String action) throws FlooringMasteryPersistenceException;

}
