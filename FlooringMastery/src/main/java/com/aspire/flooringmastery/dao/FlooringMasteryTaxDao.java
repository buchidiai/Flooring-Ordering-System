/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Tax;
import java.util.List;

/**
 *
 * @author louie
 */
public interface FlooringMasteryTaxDao {

    List<Tax> getAllTaxes();

    Tax getTax();

}
