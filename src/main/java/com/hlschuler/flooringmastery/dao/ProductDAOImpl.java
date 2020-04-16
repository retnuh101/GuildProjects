/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author hschuler2992
 */
public class ProductDAOImpl implements ProductDAO {

    public static final String TEST_FILE = "testproductfile.txt";
    public static final String DELIMITER = ",";

    private Map<String, Product> products = new HashMap<>();

    @Override
    public Product getProductInfo(String productType)
            throws FlooringPersistenceException {
        return products.get(productType);
    }
    
    @Override
    public BigDecimal getMaterialCost(String productType){
        Product product = products.get(productType);
        BigDecimal costPerSqFt = product.getCostSquareFoot();
        return costPerSqFt;
    }
    
    @Override
    public BigDecimal getLaborCost(String productType){
        Product product = products.get(productType);
        BigDecimal laborCostPerSqFt = product.getLaborCostSquareFoot();
        return laborCostPerSqFt;
    }
            
    @Override
    public Product addProductInfo(String productType, Product product) throws FlooringPersistenceException {
        Product newProduct = products.put(productType, product);
        return newProduct;
    }

    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException {
        return new ArrayList(products.values());
    }
    @Override
    public Product removeProductInfo(String productType) throws FlooringPersistenceException {
        Product removedProduct = products.remove(productType);
        return removedProduct;
    }


    private Product unmarshallProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);

        Product productFromFile = new Product();
        
        productFromFile.setProductType(productTokens[0]);
        
        String productCostAsString = productTokens[1];
        BigDecimal productCost = new BigDecimal(productCostAsString);
        productCost.toString();
        productFromFile.setCostSquareFoot(productCost);

        String laborCostAsString = productTokens[2];
        BigDecimal laborCost = new BigDecimal(laborCostAsString);
        laborCost.toString();
        productFromFile.setLaborCostSquareFoot(laborCost);

        return productFromFile;
    }
    
    @Override
    public void loadProduct() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TEST_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Could not load product data into memory.", e);
        }
        String currentLine;
        Product currentProduct;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);

            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    

    
}
