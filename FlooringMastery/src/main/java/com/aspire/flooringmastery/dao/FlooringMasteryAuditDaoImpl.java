/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Order;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 *
 * @author louie
 */
public class FlooringMasteryAuditDaoImpl implements FlooringMasteryAuditDao {

    public static final String AUDIT_FILE = "auditLog.txt";

    @Override
    public void writeAuditEntry(Order order, String action) throws FlooringMasteryPersistenceException {
        PrintWriter out;

        try {

            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not persist audit information.", e);
        }

        LocalDateTime ld = LocalDateTime.now();

        String time = ld.getDayOfWeek() + "-" + ld.getMonth() + " " + ld.getDayOfMonth() + "," + ld.getYear();
        out.println(action + "::" + order.getOrderNumber() + "::" + time + "::" + order.getCustomerName() + "::" + order.getProductType() + "::" + order.getTotal());
        out.flush();
    }

}
