/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.InventoryItem;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public interface VendingMachineDao {
    public void loadAllItems() throws VendingPersistenceException;
    
    public void saveAllChanges() throws VendingPersistenceException;
    
    public InventoryItem addItems(String itemId, InventoryItem toAdd);
    
    public List<InventoryItem> getAllItems();
    
    public InventoryItem getAnItem(String itemId);
    
    public void updateAnItem(String itemId, InventoryItem changedItem);
    
    public InventoryItem removeAnItem(String itemId);
}
