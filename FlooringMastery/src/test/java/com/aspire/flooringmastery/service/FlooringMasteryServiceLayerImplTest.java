/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.service;

import com.aspire.flooringmastery.dao.FlooringMasteryAuditDao;
import com.aspire.flooringmastery.dao.FlooringMasteryAuditDaoImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDao;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDaoImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryProductDao;
import com.aspire.flooringmastery.dao.FlooringMasteryProductDaoImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryTaxDao;
import com.aspire.flooringmastery.dao.FlooringMasteryTaxDaoImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author louie
 */
public class FlooringMasteryServiceLayerImplTest {

    private FlooringMasteryServiceLayer service;

    public FlooringMasteryServiceLayerImplTest() {

        // Instantiate the Audit DAO
        FlooringMasteryAuditDao myAudit = new FlooringMasteryAuditDaoImpl();
        //Declare a FlooringMasteryOrderDao variable and initialize it with a FlooringMasteryOrderDaoFileImpl reference.
        FlooringMasteryOrderDao myOrderDao = new FlooringMasteryOrderDaoImpl();
        //Declare a FlooringMasteryProductDao variable and initialize it with a FlooringMasteryProductDaoFileImpl reference.
        FlooringMasteryProductDao myProductDao = new FlooringMasteryProductDaoImpl();
        //Declare a FlooringMasteryTaxDao variable and initialize it with a FlooringMasteryTaxDaoFileImpl reference.
        FlooringMasteryTaxDao myTaxDao = new FlooringMasteryTaxDaoImpl();
        service = new FlooringMasteryServiceLayerImpl(myOrderDao, myProductDao, myTaxDao, myAudit);
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

}
