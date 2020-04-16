/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.service;

import com.hlschuler.flooringmastery.dao.OrderDAO;
import com.hlschuler.flooringmastery.service.stubs.OrderDAOStubImpl;
import com.hlschuler.flooringmastery.dao.ProductDAO;
import com.hlschuler.flooringmastery.service.stubs.ProductDAOStubImpl;
import com.hlschuler.flooringmastery.dao.TaxDAO;
import com.hlschuler.flooringmastery.service.stubs.TaxDAOStubImpl;
import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Product;
import com.hlschuler.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import static java.text.DateFormat.Field.MONTH;
import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hschuler2992
 */
public class ServiceLayerTest {
    
    private ServiceLayerImpl testService;
    
    Order testOrderOne;
    Order testOrderTwo;
    
    Product testProduct;
    
    Tax testTax;

    public ServiceLayerTest(){
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        testOrderOne = new Order(date1, 1, "Erin", "IN", 
                new BigDecimal("3.14"), "Tile", new BigDecimal("100"), 
                new BigDecimal("2.19"), new BigDecimal("3.19"), new BigDecimal("300"),
                new BigDecimal("350"), new BigDecimal("380"), new BigDecimal("800"),
                new BigDecimal("800"));
        LocalDate date2 = LocalDate.of(2020, 1, 1);
        testOrderTwo = new Order(date1, 2, "Jared", "KY", 
                new BigDecimal("5.14"), "Trampoline", new BigDecimal("200"), 
                new BigDecimal("3.19"), new BigDecimal("4.19"), new BigDecimal("400"),
                new BigDecimal("450"), new BigDecimal("480"), new BigDecimal("900"),
                new BigDecimal("1000"));
        testProduct = new Product("Tile", new BigDecimal("2.14"), new BigDecimal("3.14"));
        testTax = new Tax("IN", "Indiana", new BigDecimal("3.14"));
        
        OrderDAOStubImpl oStub = new OrderDAOStubImpl(testOrderOne, testOrderTwo);
        ProductDAOStubImpl pStub = new ProductDAOStubImpl(testProduct);
        TaxDAOStubImpl tStub = new TaxDAOStubImpl(testTax);
        
        testService = new ServiceLayerImpl(oStub, pStub, tStub);
    }
    

    @Test
    public void testMaterialCost(){
        //Arrange
        BigDecimal expectedMC = new BigDecimal("219.00");
        //Act
        BigDecimal materialCost = testService.calculateMaterialCost(testOrderOne, testOrderOne.getArea(), testOrderOne.getCostPerSquareFoot());
        //Assert
        Assertions.assertEquals(expectedMC, materialCost, "Material cost should be 219.00.");
    }
    
    @Test
    public void testCalculateLaborCost(){
        //Arrange
        BigDecimal expectedLC = new BigDecimal("319.00");
        //Act
        BigDecimal laborCost = testService.calculateLaborCost(testOrderOne, testOrderOne.getArea(), testOrderOne.getLaborCostPerSquareFoot());
        //Assert
        Assertions.assertEquals(expectedLC, laborCost, "Labor cost should be 319.00 ");
    }
    
    @Test
    public void testCalculateTax(){
        //Arrange
        BigDecimal expectedTax = new BigDecimal("27.30");
        //Act
        
        BigDecimal tax = testService.calculateTax(testOrderOne, testOrderOne.getMaterialCost(), testOrderOne.getLaborCost());
        //Assert
        Assertions.assertEquals(expectedTax, tax, "Tax should be ");
    }
    
    @Test
    public void testCalculateTotal(){
        //Arrange
        BigDecimal expectedTotal = new BigDecimal("1180.00");
        //Act
        BigDecimal total = testService.calculateTotal(testOrderOne, testOrderOne.getMaterialPlusLabor(), testOrderOne.getTax());
        //Assert
        Assertions.assertEquals(expectedTotal, total, "Total should be 1180.00.");
    }
}
