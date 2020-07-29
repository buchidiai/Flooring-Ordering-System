/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery;

import com.aspire.flooringmastery.controller.FlooringMasteryController;
import com.aspire.flooringmastery.dao.FlooringMasteryAuditDao;
import com.aspire.flooringmastery.dao.FlooringMasteryAuditDaoImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDao;
import com.aspire.flooringmastery.dao.FlooringMasteryOrderDaoImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryProductDao;
import com.aspire.flooringmastery.dao.FlooringMasteryProductDaoImpl;
import com.aspire.flooringmastery.dao.FlooringMasteryTaxDao;
import com.aspire.flooringmastery.dao.FlooringMasteryTaxDaoImpl;
import com.aspire.flooringmastery.service.FlooringMasteryServiceLayer;
import com.aspire.flooringmastery.service.FlooringMasteryServiceLayerImpl;
import com.aspire.flooringmastery.ui.FlooringMasteryView;
import com.aspire.flooringmastery.ui.UserIO;
import com.aspire.flooringmastery.ui.UserIOConsoleImpl;

/**
 *
 * @author louie
 */
public class App {

    public static void main(String[] args) {

        //Declare a UserIO variable and initialize it with a UserIOConsoleImpl reference
        UserIO myIo = new UserIOConsoleImpl();

        //Declare and instantiate a FlooringMasteryView object, passing the UserIO created in the previous step into the constructor.
        FlooringMasteryView myView = new FlooringMasteryView(myIo);

        // Instantiate the Audit DAO
        FlooringMasteryAuditDao myAudit = new FlooringMasteryAuditDaoImpl();

        //Declare a FlooringMasteryOrderDao variable and initialize it with a FlooringMasteryOrderDaoFileImpl reference.
        FlooringMasteryOrderDao myOrderDao = new FlooringMasteryOrderDaoImpl();

        //Declare a FlooringMasteryProductDao variable and initialize it with a FlooringMasteryProductDaoFileImpl reference.
        FlooringMasteryProductDao myProductDao = new FlooringMasteryProductDaoImpl();

        //Declare a FlooringMasteryTaxDao variable and initialize it with a FlooringMasteryTaxDaoFileImpl reference.
        FlooringMasteryTaxDao myTaxDao = new FlooringMasteryTaxDaoImpl();

        //Instantiate a FlooringMasteryServiceLayer, passing the FlooringMasteryOrderDao and FlooringMasteryAuditDai object into the constructor.
        FlooringMasteryServiceLayer serverLayer = new FlooringMasteryServiceLayerImpl(myOrderDao, myProductDao, myTaxDao, myAudit);

        //Instantiate a FlooringMasteryController, passing the FlooringMasteryDao and FlooringMasteryView object into the constructor.
        FlooringMasteryController controller = new FlooringMasteryController(myView, serverLayer);
        controller.run();

    }
}
