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
public class FlooringMasteryAuditDaoImpl implements FlooringMasteryAuditDao {

    public static final String AUDIT_FILE = "auditactivity.txt";

    @Override
    public void writeAuditEntry(Order order) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
