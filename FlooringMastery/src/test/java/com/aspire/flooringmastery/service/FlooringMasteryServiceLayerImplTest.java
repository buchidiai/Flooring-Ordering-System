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

    @BeforeEach
    public void setUp() {
    }

//    @Test
//    public void testAddOrder() throws Exception {
//        // ARRANGE
//
//
//        BigDecimal productPrice = new BigDecimal("12.99");
//        String productName = "Wine";
//
//        Product testProductToSell = new Product(productName, productPrice, 2);
//
//        BigDecimal moneyInserted = new BigDecimal("15.99");
//
//        BigDecimal moneyLeft = moneyInserted.subtract(productPrice);
//
//        Change change = new Change(moneyLeft);
//
//        // ACT & ASSERT
//        try {
//            saleResponse = service.sellProduct(moneyInserted, testProductToSell);
//
//        } catch (VendingMachineInsufficentFundsException | VendingMachinePersistenceException | VendingMachineNoItemInventoryException e) {
//
//            System.out.println(" Error: " + e.getMessage());
//        }
//
//        assertEquals(moneyInserted, saleResponse.getMoneyEntered(),
//                "Money entered should be same returned.");
//
//        assertTrue(change.equals(saleResponse.getChange()),
//                "change should be correct.");
//
//        assertEquals(moneyLeft, saleResponse.getFullChange(),
//                "money left should be the same an full change.");
//
//        assertEquals(productName, saleResponse.getProductName(),
//                "product name should be the same.");
//    }
}
