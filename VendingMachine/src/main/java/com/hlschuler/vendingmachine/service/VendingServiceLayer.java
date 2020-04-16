/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.vendingmachine.service;


import com.hlschuler.vendingmachine.Coins;
import dto.ChangePurse;
import dto.InventoryItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public interface VendingServiceLayer {
    public void loadMachine()
        throws VendingPersistenceException;
    
    public List<InventoryItem> getAllItemsinMachine();
    public InventoryItem getOneItem(String itemId);
    
    public String purchaseItem(int itemSelection)
        throws VendingPersistenceException,
            VendingInsufficientFundsException,
            VendingNoItemInventoryException;
    public void addCoin(Coins coin);
    //public InventoryItem itemSelection(String itemId, int vendingSelection);
    public BigDecimal getBalance();
    public String returnCoins();
}
