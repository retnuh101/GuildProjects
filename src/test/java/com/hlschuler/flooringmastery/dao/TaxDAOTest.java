/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Tax;
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
public class TaxDAOTest {
    
    private TaxDAO dao = new TaxDAOImpl();
    
    public TaxDAOTest() {
    }
    
    
    
    @BeforeEach
    public void setUp() throws Exception{
        List<Tax>taxList = dao.getAllTaxInfo();
        for (Tax tax : taxList){
            dao.removeTaxInfo(tax.getStateName());
        }
    }
    
    

    /**
     * Test of getTaxInfo method, of class TaxDAO.
     */
    @Test
    public void testGetTaxInfo() throws Exception {
        Tax tax = new Tax();
        tax.setStateAbbreviation("CA");
        tax.setStateName("California");
        BigDecimal taxRate = new BigDecimal("4.00");
        tax.setTaxRate(taxRate);
        
        dao.addTaxInfo(tax.getStateName(), tax);
        
        Tax fromDao = dao.getTaxInfo(tax.getStateName());
        
        Assertions.assertEquals(tax, fromDao);
    }

    

   
    
}
