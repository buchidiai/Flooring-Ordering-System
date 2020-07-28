/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.controller;

import com.aspire.flooringmastery.ui.FlooringMasteryView;

/**
 *
 * @author louie
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryView view) {
        this.view = view;
    }

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;

        while (keepGoing) {

            menuSelection = getMenuSelection();

            switch (menuSelection) {
                case 1:
                    displayOrder();
                    break;
                case 2:
                    addOrder();

                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();

                    break;
                case 5:
                    exportAllData();

                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }

        }
        exitMessage();
    }

    private int getMenuSelection() {

        return view.printMenuAndGetSelection();
    }

    private void displayOrder() {
        System.out.println("display orders");
    }

    private void addOrder() {
        System.out.println("add orders");
    }

    private void editOrder() {
        System.out.println("edit orders");
    }

    private void removeOrder() {
        System.out.println("remove orders");
    }

    private void exportAllData() {
        System.out.println("Export all Data");
    }

    private void unknownCommand() {
        System.out.println("Unknown command");
    }

    private void exitMessage() {
        System.out.println("goodbye");
    }

}
