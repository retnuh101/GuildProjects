/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.vendingmachine.service;

import dao.VendingMachineDaoStubImpl;
import dto.InventoryItem;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;


/**
 *
 * @author hschuler2992
 */
public class VendingServiceLayerTest {
    
    VendingServiceLayerImpl service;
    InventoryItem testItemOne;
    InventoryItem testItemTwo;
    
    
    public VendingServiceLayerTest() {
//        testCritterOne = new Critter(1, "Pippa", "Peahen",
//                Diet.HERBIVORE, new BigDecimal("20.00"));
//        testCritterTwo = new Critter(2, "Bitey", "Tiger",
//                Diet.CARNIVORE, new BigDecimal("100.00"));
//
//        testHabitat = new Habitat(42, 5, new ArrayList<>());
//
//        CritterDAOStub cStub = new CritterDAOStub(testCritterOne, testCritterTwo);
//        HabitatDAOStub hStub = new HabitatDAOStub(testHabitat);
//        testService = new ZooServiceImpl(cStub, hStub);
        BigDecimal price = new BigDecimal("3.00");
        testItemOne = new InventoryItem("1", "iPhone", price, 10);
        BigDecimal price2 = new BigDecimal("5.00");
        testItemTwo = new InventoryItem("2", "Samsung Galaxy", price2, 15);
        
        VendingMachineDaoStubImpl vStub = new VendingMachineDaoStubImpl(testItemOne, testItemTwo);
    }
    
    @Test
    public void testPurchaseItemThatDoesntExist() throws Exception {
        
    }

    

    
    
}
