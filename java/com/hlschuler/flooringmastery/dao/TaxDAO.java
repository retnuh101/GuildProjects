/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public interface TaxDAO {
    
    Tax getTaxInfo(String state) throws FlooringPersistenceException;
    
    Tax addTaxInfo(String state, Tax tax) throws FlooringPersistenceException;
    
    List<Tax> getAllTaxInfo() throws FlooringPersistenceException;;
    
    Tax removeTaxInfo(String state) throws FlooringPersistenceException;
    
    public void loadTax() throws FlooringPersistenceException;
    
    public BigDecimal getTaxRate(String state);
}
