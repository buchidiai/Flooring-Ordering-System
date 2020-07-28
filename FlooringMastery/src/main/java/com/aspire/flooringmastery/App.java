/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery;

import com.aspire.flooringmastery.controller.FlooringMasteryController;
import com.aspire.flooringmastery.ui.FlooringMasteryView;
import com.aspire.flooringmastery.ui.UserIO;
import com.aspire.flooringmastery.ui.UserIOConsoleImpl;

/**
 *
 * @author louie
 */
public class App {

    public static void main(String[] args) {

        UserIO io = new UserIOConsoleImpl();

        FlooringMasteryView view = new FlooringMasteryView(io);

        FlooringMasteryController controller = new FlooringMasteryController(view);
        controller.run();

    }
}
