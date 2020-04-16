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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hschuler2992
 */
public class ProductDAOTest {
    
    private ProductDAO dao = new ProductDAOImpl();
    
    public ProductDAOTest() {
    }
    
    
    
    @BeforeEach
    public void setUp() throws Exception{
        List<Product>productList = dao.getAllProducts();
        for (Product product : productList){
            dao.removeProductInfo(product.getProductType());
        }
    }
    
   

    /**
     * Test of getProductInfo method, of class ProductDAO.
     */
    @Test
    public void testGetProductInfo() throws Exception {
        Product product = new Product();
        product.setProductType("Wood");
        BigDecimal costPerSquareFoot = new BigDecimal("3.50");
        product.setCostSquareFoot(costPerSquareFoot);
        BigDecimal laborCostPerSquareFoot = new BigDecimal("5.00");
        product.setLaborCostSquareFoot(laborCostPerSquareFoot);
        
        dao.addProductInfo(product.getProductType(), product);
        
        Product fromDao = dao.getProductInfo(product.getProductType());
        
        Assertions.assertEquals(product, fromDao);
    }

    
    
}
