/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspire.flooringmastery.dao;

import com.aspire.flooringmastery.model.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author louie
 */
public class FlooringMasteryProductDaoStubImpl implements FlooringMasteryProductDao {

    public Product onlyProduct;

    public FlooringMasteryProductDaoStubImpl() {
        this.onlyProduct = new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15"));
    }

    public FlooringMasteryProductDaoStubImpl(Product testProduct) {
        this.onlyProduct = testProduct;
    }

    @Override
    public Product getProduct(String productType) throws FlooringMasteryPersistenceException {
        List<Product> productList = new ArrayList<>();

        productList.add(onlyProduct);

        Product product = null;

        for (Product p : productList) {
            if (p.getProductType().equals(productType)) {
                product = p;
                break;
            }
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        List<Product> productList = new ArrayList<>();

        productList.add(onlyProduct);

        return productList;
    }

}
