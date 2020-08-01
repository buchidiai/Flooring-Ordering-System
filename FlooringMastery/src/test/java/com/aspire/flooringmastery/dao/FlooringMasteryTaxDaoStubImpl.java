/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Tax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryTaxDaoStubImpl implements FlooringMasteryTaxDao {

    public Tax onlyTax;

    public FlooringMasteryTaxDaoStubImpl() {
        this.onlyTax = new Tax("TX", "Texas", new BigDecimal("4.45"));
    }

    public FlooringMasteryTaxDaoStubImpl(Tax testTax) {
        this.onlyTax = testTax;
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        List<Tax> taxList = new ArrayList<>();

        taxList.add(onlyTax);

        return taxList;
    }

    @Override
    public List<String> getAllStates() throws FlooringMasteryPersistenceException {
        List<String> allStates = new ArrayList<>();

        allStates.add(onlyTax.getStateName());

        return allStates;

    }

    @Override
    public Tax getTax(String state) throws FlooringMasteryPersistenceException {
        List<Tax> taxList = new ArrayList<>();

        taxList.add(onlyTax);

        Tax tax = null;

        for (Tax t : taxList) {
            if (t.getStateAbbreviation().equals(state)) {
                tax = t;
                break;
            }
        }
        return tax;

    }

}
