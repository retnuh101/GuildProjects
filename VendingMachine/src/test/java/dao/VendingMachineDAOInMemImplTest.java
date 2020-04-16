/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.InventoryItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineDAOInMemImplTest {

    private VendingMachineDao dao = new VendingMachineFileImpl();

    public VendingMachineDAOInMemImplTest() {
    }

    @BeforeEach
    public void setUp() throws Exception {
        List<InventoryItem> itemList = dao.getAllItems();
        for (InventoryItem item : itemList) {
            dao.removeAnItem(item.getItemId());
        }
    }

    @Test
    public void testAddGetItem() throws Exception {
        //arrange
        InventoryItem item = new InventoryItem("1");
        item.setItemName("LG Smartphone");
        BigDecimal price = new BigDecimal("5.00");
        item.setItemPrice(price);
        item.setItemQuantity(5);
        //act
        dao.addItems(item.getItemId(), item);
        InventoryItem fromDao = dao.getAnItem(item.getItemId());
        //assert
        Assertions.assertEquals(item, fromDao);

    }
    
    @Test
    public void testGetAllItems() throws Exception {
        InventoryItem item1 = new InventoryItem("1");
        item1.setItemName("Huawei Foldable Phone");
        BigDecimal price1 = new BigDecimal("10.00");
        item1.setItemPrice(price1);
        item1.setItemQuantity(1);
        
        dao.addItems(item1.getItemId(), item1);
        
        InventoryItem item2 = new InventoryItem("2");
        item2.setItemName("KITT");
        BigDecimal price2 = new BigDecimal("100000.00");
        item2.setItemPrice(price2);
        item2.setItemQuantity(1);
        
        dao.addItems(item2.getItemId(), item2);
        
        Assertions.assertEquals(2, dao.getAllItems().size());
        
    }
    
    @Test
    public void testRemoveStudent() throws Exception {
        InventoryItem item1 = new InventoryItem("1");
        item1.setItemName("Huawei Foldable Phone");
        BigDecimal price1 = new BigDecimal("10.00");
        item1.setItemPrice(price1);
        item1.setItemQuantity(1);
        
        dao.addItems(item1.getItemId(), item1);
        
        InventoryItem item2 = new InventoryItem("2");
        item2.setItemName("KITT");
        BigDecimal price2 = new BigDecimal("100000.00");
        item2.setItemPrice(price2);
        item2.setItemQuantity(1);
        
        dao.addItems(item2.getItemId(), item2);
        
        dao.removeAnItem(item1.getItemId());
        
        Assertions.assertEquals(1, dao.getAllItems().size());
        Assertions.assertNull(dao.getAnItem(item1.getItemId()));
        
        dao.removeAnItem(item2.getItemId());
        
        Assertions.assertEquals(0, dao.getAllItems().size());
        Assertions.assertNull(dao.getAnItem(item2.getItemId()));
    }
    
    @Test
    public void addUpdateItemTest() throws Exception {
        //ARRANGE
        String itemId = "1";
        InventoryItem item1 = new InventoryItem(itemId);
        item1.setItemName("Huawei Foldable Phone");
        BigDecimal price1 = new BigDecimal("10.00");
        item1.setItemPrice(price1);
        item1.setItemQuantity(1);
        
        String itemId2 = "2";
        InventoryItem item2 = new InventoryItem(itemId);
        item2.setItemName("KITT");
        BigDecimal price2 = new BigDecimal("100000.00");
        item2.setItemPrice(price2);
        item2.setItemQuantity(1);
        
        //ACT
        dao.addItems(itemId, item1);
        dao.updateAnItem(itemId, item2);
        
        InventoryItem retrieved = dao.getAnItem(itemId);
        List<InventoryItem> allTheItems = dao.getAllItems();
        
        //ASSERT
        Assertions.assertEquals(item2, retrieved, "Item should have been replaced by second.");
        Assertions.assertEquals(1, allTheItems.size(), "There should only be one item.");
        Assertions.assertTrue(allTheItems.contains(item2), "Only item left should be second one.");
    }
    
    @Test
    public void emtyDAOTest(){
        //Arrange - done in constructor
        //Act
        List<InventoryItem> emptyInventory = dao.getAllItems();
        //Assert
        Assertions.assertNotNull(emptyInventory, "Should be empty list, not null.");
        Assertions.assertEquals(0, emptyInventory.size(), "Should be an empty list.");
    }

            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
}
