/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author louie
 */
@Component
public class FlooringMasteryProductDaoImpl implements FlooringMasteryProductDao {

    private final String PRODUCT_FILE;
    private static final String PRODUCT_FOLDER = "Data/";
    private static final String DELIMITER = ",";
    private static final int NUMBER_OF_FIELDS = 3;

    private List<Product> products = new ArrayList<>();

    @Autowired
    public FlooringMasteryProductDaoImpl() {
        this.PRODUCT_FILE = "Products.txt";
    }

    public FlooringMasteryProductDaoImpl(String PRODUCT_FILE) {
        this.PRODUCT_FILE = PRODUCT_FILE;
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        loadProducts();
        return products;
    }

    @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        loadProducts();
        Product product = null;

        for (Product p : products) {
            if (p.getProductType().equals(productType)) {
                product = p;
            }
        }

        return product;
    }

    private void loadProducts() throws FlooringMasteryPersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FOLDER + PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "-_- Could not load product data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentProduct holds the most recent product unmarshalled
        Product currentProduct;
        // Go through ROSTER_FILE line by line, decoding each line into a
        // Product object by calling the unmarshallProduct method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();

            // unmarshall the line into a Product
            currentProduct = unmarshallProduct(currentLine);

            // Put currentProduct into the map using product id as the key
            if (currentProduct != null) {
                products.add(currentProduct);
            }

        }
        // close scanner
        scanner.close();
    }

    private Product unmarshallProduct(String productAsText) {

        // productAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // chips::2.50::10
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in productTokens.
        // Which should look like this:
        // ___________________
        // |      |    |      |
        // |Carpet|2.50| 2.10 |
        // |      |    |      |
        // --------------------
        //  [0]  [1]    [2]
        String[] productTokens = productAsText.split(DELIMITER);

        Product productFromFile = null;

        if (productTokens.length == NUMBER_OF_FIELDS) {

            //on load up size is zero so read from file
            if (products.size() == 0) {

                //create Product object
                productFromFile = createProductObject(productTokens);

            } else {
                //size is not zero
                //check for duplicates for list
                for (Product p : products) {
                    //if name in list (cleaned up with regex)  == name in file (cleaned up with regex)
                    if (p.getProductType().equals(productTokens[0])) {
                        return null;
                    } else {
                        //create Product object
                        productFromFile = createProductObject(productTokens);
                    }
                }
            }
        } else {
            return null;
        }
        // We have now created a product! Return it!
        return productFromFile;
    }

    private Product createProductObject(String[] productTokens) {
        int count = 0;
        Product productFromFile = null;

        try {

            //product type
            String productType = productTokens[0];

            //produce CostPerSquareFoot
            BigDecimal productCostPerSquareFoot = new BigDecimal(productTokens[1]);

            //produce LaborCostPerSquareFoot
            BigDecimal productLaborCostPerSquareFoot = new BigDecimal(productTokens[2]);

            // Which we can then use to create a new Product object to satisfy
            // the requirements of the Product constructor.
            productFromFile = new Product(productType, productCostPerSquareFoot, productLaborCostPerSquareFoot);
        } catch (Exception e) {

            count++;

            if (count <= 1) {
                System.out.println("");
                System.out.println("Unable to load " + productTokens[0] + ", it has been removed from inventory");
            }

        }

        return productFromFile;

    }

}
