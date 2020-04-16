/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public interface ProductDAO {
    
    Product getProductInfo(String productType) throws FlooringPersistenceException;
    
    Product addProductInfo(String productType, Product product) throws FlooringPersistenceException;
    
    List<Product> getAllProducts() throws FlooringPersistenceException;
    
    Product removeProductInfo(String productType) throws FlooringPersistenceException;
    
    public void loadProduct() throws FlooringPersistenceException;
    
    public BigDecimal getMaterialCost(String productType);
    
    public BigDecimal getLaborCost(String productType);
}
