/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.InventoryItem;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineFileImplTest {
    
    VendingMachineFileImpl testDao;
    
    public VendingMachineFileImplTest() {
        testDao = new VendingMachineFileImpl();
    }

    @Test
    public void unMarshallInventoryItemTest() {
        //Arrange
        String inventoryItemLine = "1::iPhone::3.00::10";
        //Act
        InventoryItem fromLine = testDao.unmarshallItems(inventoryItemLine);
        //Assert
        Assertions.assertEquals("1", fromLine.getItemId(), "ID should be 1.");
        Assertions.assertEquals("iPhone", fromLine.getItemName(), "Item name should be iPhone.");
        BigDecimal price = new BigDecimal("3.00");
        Assertions.assertEquals(price, fromLine.getItemPrice(), "Item price should be 3.00");
        Assertions.assertEquals(10, fromLine.getItemQuantity(), "Item quantity should be 10.");
    }
    
    @Test
    public void marshallInventoryItemTest(){
        //Arrange
        BigDecimal price = new BigDecimal("3.00");
        InventoryItem toTextify = new InventoryItem("MYID", "MYNAME", price, 10);
        //Act
        String inventoryItemAsText = testDao.marshallItems(toTextify);
        //Assert
        Assertions.assertEquals("MYID::MYNAME::3.00::10", inventoryItemAsText, "Lines should match!");
    }
    
    
    
}
