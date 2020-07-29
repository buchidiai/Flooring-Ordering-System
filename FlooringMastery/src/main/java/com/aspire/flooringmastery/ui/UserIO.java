/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.ui;

import java.math.BigDecimal;

/**
 *
 * @author louie
 */
public interface UserIO {

    void print(String message);

    String readString(String prompt);

    boolean readYesOrNo(String prompt);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    BigDecimal readBigDecimal(String prompt);

    String readDate(String Prompt);

    String readDateForOrders(String Prompt);

}
