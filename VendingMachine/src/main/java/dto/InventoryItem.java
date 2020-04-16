/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.math.BigDecimal;

/**
 *
 * @author hschuler2992
 */
public class InventoryItem {
    private String itemId;
    private String itemName;
    private BigDecimal itemPrice;
    private int itemQuantity;

    public InventoryItem(){}

    public InventoryItem(String itemId, String itemName, BigDecimal itemPrice, int itemQuantity) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }
     
    public InventoryItem(String itemId){
        this.itemId = itemId;
    }
    
    public String getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }
    
    
}
