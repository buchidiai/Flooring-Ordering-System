/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.service;

import com.aspire.flooringmastery.dao.FlooringMasteryAuditDao;
import com.aspire.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.aspire.flooringmastery.model.Order;

/**
 *
 * @author louie
 */
public class FlooringMasteryAuditDaoStubImpl implements FlooringMasteryAuditDao {

    @Override
    public void writeAuditEntry(Order order, String action) throws FlooringMasteryPersistenceException {

    }

}
