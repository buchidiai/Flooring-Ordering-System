/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.service;

/**
 *
 * @author louie
 */
public class FlooringMasteryInvalidProductTypeException extends Exception {

    public FlooringMasteryInvalidProductTypeException(String message) {
        super(message);
    }

    public FlooringMasteryInvalidProductTypeException(String message, Throwable cause) {
        super(message, cause);
    }

}
