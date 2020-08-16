/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.service;

import com.hlschuler.flooringmastery.dao.FlooringPersistenceException;
import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Product;
import com.hlschuler.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public interface ServiceLayer {
    
    Order addOrder(Order currentOrder) 
            throws FlooringPersistenceException,
            OrderDataValidationException,
            OrderNumberDuplicationException;
    
    BigDecimal calculateTax(Order newOrder, BigDecimal materialCost, BigDecimal laborCost);
    
    BigDecimal getSetMaterialCostPerSquareFoot(Order newOrder);
    BigDecimal getSetLaborCostPerSquareFoot(Order newOrder);
    
    List<Order> getOrdersForDate(LocalDate date) throws FlooringPersistenceException;
    
    Order getOrder(int orderNumber) throws FlooringPersistenceException;
    
    Order editOrder(int orderNumber, Order toUpdate) throws FlooringPersistenceException;
    
    Order removeOrder(int orderNumber) throws FlooringPersistenceException;
    
    public void loadAll() throws FlooringPersistenceException;
    
    public BigDecimal calculateMaterialCost(Order newOrder, BigDecimal area, BigDecimal materialCostPerSquareFoot);
    
    public BigDecimal calculateLaborCost(Order newOrder, BigDecimal laborCostPerSquareFoot, BigDecimal area);
            
    public BigDecimal calculateTotal(Order newOrder, BigDecimal materialPlusLabor, BigDecimal tax);
    
    public void saveCurrentWork() throws FlooringPersistenceException;
    
    public BigDecimal calculateMaterialCostPlusLabor(Order newOrder, BigDecimal materialCost, BigDecimal laborCost);
    
    public boolean getTrainingMode() throws FlooringPersistenceException;
    
    public List<Product> getAllProducts() throws FlooringPersistenceException;
    
    public List<Tax> getAllTax() throws FlooringPersistenceException;
}
