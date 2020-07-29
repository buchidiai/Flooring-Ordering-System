/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author louie
 */
public class FlooringMasteryTaxDaoImpl implements FlooringMasteryTaxDao {

    private final String TAX_FILE;
    private final String TAX_FOLDER = "Data/";
    private static final String DELIMITER = ",";
    public static final int NUMBER_OF_FIELDS = 3;

    private List<Tax> taxes = new ArrayList<>();

    public FlooringMasteryTaxDaoImpl() {
        this.TAX_FILE = "Taxes.txt";
    }

    public FlooringMasteryTaxDaoImpl(String TAX_FILE) {
        this.TAX_FILE = TAX_FILE;
    }

    @Override
    public List<Tax> getAllTaxes() throws FlooringMasteryPersistenceException {
        loadTaxes();
        return taxes;
    }

    @Override
    public Tax getTax(String state) throws FlooringMasteryPersistenceException {
        loadTaxes();

        Tax tax = null;

        for (Tax t : taxes) {
            if (t.getStateAbbreviation().equals(state)) {
                tax = t;
            }
        }

        return tax;
    }

    @Override
    public List<String> getAllStates() throws FlooringMasteryPersistenceException {
        loadTaxes();
        List<String> states = new ArrayList<>();

        for (Tax t : taxes) {
            states.add(t.getStateAbbreviation());
        }

        return states;
    }

    private void loadTaxes() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_FOLDER + TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not tax data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentTax holds the most recent tax unmarshalled
        Tax currentTax;
        // Go through TAX_FILE line by line, decoding each line into a
        // Tax object by calling the unmarshallTax method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();

            // unmarshall the line into a Tax
            currentTax = unmarshallTax(currentLine);

            // Put currentTax into the map using tax id as the key
            if (currentTax != null) {
                taxes.add(currentTax);
            }

        }
        // close scanner
        scanner.close();
    }

    private Tax unmarshallTax(String taxAsText) {

        // taxAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // TX,Texas,4.45
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in taxTokens.
        // Which should look like this:
        //State,StateName,TaxRate
        // ___________________
        // |      |     |      |
        // |TX    |Texas| 4.45 |
        // |      |     |      |
        // --------------------
        //  [0]  [1]    [2]
        String[] taxTokens = taxAsText.split(DELIMITER);

        Tax taxFromFile = null;

        if (taxTokens.length == NUMBER_OF_FIELDS) {

            //on load up size is zero so read from file
            if (taxes.size() == 0) {

                //create Tax object
                taxFromFile = createTaxObject(taxTokens);

            } else {
                //size is not zero
                //check for duplicates for list
                for (Tax t : taxes) {
                    //if name in list (cleaned up with regex)  == name in file (cleaned up with regex)
                    if (t.getStateAbbreviation().equals(taxTokens[0])) {
                        return null;
                    } else {
                        //create Tax object
                        taxFromFile = createTaxObject(taxTokens);
                    }
                }
            }
        } else {
            return null;
        }
        // We have now created a tax! Return it!
        return taxFromFile;
    }

    private Tax createTaxObject(String[] taxTokens) {
        int count = 0;
        Tax taxFromFile = null;

        try {

            //tax type
            String stateAbbreviation = taxTokens[0];

            //produce CostPerSquareFoot
            String stateName = taxTokens[1];

            //produce LaborCostPerSquareFoot
            BigDecimal taxRate = new BigDecimal(taxTokens[2]);

            // Which we can then use to create a new Tax object to satisfy
            // the requirements of the Tax constructor.
            taxFromFile = new Tax(stateAbbreviation, stateName, taxRate);
        } catch (Exception e) {

            count++;

            if (count <= 1) {
                System.out.println("");
                System.out.println("Unable to load " + taxTokens[0] + ", it has been removed from inventory");
            }

        }

        return taxFromFile;

    }

}
