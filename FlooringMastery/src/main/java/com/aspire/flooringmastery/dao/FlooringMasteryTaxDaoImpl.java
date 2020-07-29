/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Product;
import com.aspire.flooringmastery.model.Tax;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryTaxDaoImpl implements FlooringMasteryTaxDao {

    private final String TAX_FILE;
    private static final String DELIMITER = ",";
    public static final int NUMBER_OF_FIELDS = 3;

    private List<Product> taxes = new ArrayList<>();

    public FlooringMasteryTaxDaoImpl() {
        this.TAX_FILE = "Taxes.txt";
    }

    public FlooringMasteryTaxDaoImpl(String TAX_FILE) {
        this.TAX_FILE = TAX_FILE;
    }

    @Override
    public List<Tax> getAllTaxes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tax getTax() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
