/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.model;

import java.math.BigDecimal;

/**
 *
 * @author louie
 */
public class Tax {

    private String StateAbbreviation;
    private String StateName;
    private BigDecimal TaxRate;

    public Tax(String StateAbbreviation, String StateName, BigDecimal TaxRate) {
        this.StateAbbreviation = StateAbbreviation;
        this.StateName = StateName;
        this.TaxRate = TaxRate;
    }

    public String getStateAbbreviation() {
        return StateAbbreviation;
    }

    public String getStateName() {
        return StateName;
    }

    public BigDecimal getTaxRate() {
        return TaxRate;
    }

}
