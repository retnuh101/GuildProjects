/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.InventoryItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao{
    
    public InventoryItem onlyItem;
    
    List<InventoryItem> itemList = new ArrayList<>();
    
    public VendingMachineDaoStubImpl(){
        onlyItem = new InventoryItem("1");
        onlyItem.setItemName("iPhone");
        BigDecimal price = new BigDecimal("3.00");
        onlyItem.setItemPrice(price);
        onlyItem.setItemQuantity(10);
        
        itemList.add(onlyItem);
        
    }
    
    public VendingMachineDaoStubImpl(InventoryItem onlyItem) {
        this.onlyItem = onlyItem;
    }
    
    public VendingMachineDaoStubImpl(InventoryItem testItemOne, InventoryItem testItemTwo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadAllItems() throws VendingPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void saveAllChanges() throws VendingPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InventoryItem addItems(String itemId, InventoryItem toAdd) {
        if (itemId.equals(onlyItem.getItemId())){
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<InventoryItem> getAllItems() {
        return itemList;
    }

    @Override
    public InventoryItem getAnItem(String itemId) {
        if (itemId.equals(onlyItem.getItemId())){
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public void updateAnItem(String itemId, InventoryItem changedItem) {
        if(itemId == onlyItem.getItemId()) onlyItem = changedItem;
    }

    @Override
    public InventoryItem removeAnItem(String itemId) {
        if (itemId.equals(onlyItem.getItemId())){
            return onlyItem;
        } else {
            return null;
        }
    }
    
}
