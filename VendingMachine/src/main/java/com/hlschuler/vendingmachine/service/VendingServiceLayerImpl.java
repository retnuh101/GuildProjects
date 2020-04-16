/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.vendingmachine.service;

import com.hlschuler.vendingmachine.Coins;
import com.hlschuler.vendingmachine.controller.VendingMachineController;
import dao.VendingMachineAuditDao;
import dao.VendingMachineDao;
import dto.ChangePurse;
import dto.InventoryItem;
import java.math.BigDecimal;
import java.util.List;
import ui.UserIO;
import ui.VendingMachineView;

/**
 *
 * @author hschuler2992
 */
public class VendingServiceLayerImpl implements VendingServiceLayer {

    VendingMachineDao dao;

    BigDecimal userBalance = new BigDecimal(0);

    public VendingServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        //this auditDao = auditDao
    }

    @Override
    public void loadMachine() throws VendingPersistenceException {
        //Get List from MemDao

        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<InventoryItem> getAllItemsinMachine() {
        return dao.getAllItems();
    }

    @Override
    public InventoryItem getOneItem(String itemId) {
        return dao.getAnItem(itemId);
    }

    @Override
    public String purchaseItem(int itemSelection) throws VendingPersistenceException,
            VendingInsufficientFundsException,
            VendingNoItemInventoryException {

        String result = "";
        String itemSelectionAsString = Integer.toString(itemSelection);
        InventoryItem item = dao.getAnItem(itemSelectionAsString);
        if (item.getItemQuantity() > 0) { 
            //if money inserted >= cost of item
            if (userBalance.compareTo(item.getItemPrice()) == 1 || userBalance.compareTo(item.getItemPrice()) == 0) {
                //decriment quantity
                item.setItemQuantity(item.getItemQuantity() - 1);
                //calculate change
                userBalance = userBalance.subtract(item.getItemPrice());
                //convert change to string
                //return change
                result = returnCoins();
                //reset userBalance to 0
            } //throw VendingInsufficientFundsException; 

        } else {
            //TODO: throw VendingNoItemInventoryException
        }
        return result;
    }

    @Override
    public void addCoin(Coins coin) {
        switch (coin) {
            case QUARTERS:
                BigDecimal quarter = new BigDecimal(.25);
                userBalance = userBalance.add(quarter);
                break;
            case DIMES:
                BigDecimal dime = new BigDecimal(.10);
                userBalance = userBalance.add(dime);
                break;
            case NICKELS:
                BigDecimal nickel = new BigDecimal(.05);
                userBalance = userBalance.add(nickel);
                break;
            case PENNIES:
                BigDecimal penny = new BigDecimal(.01);
                userBalance = userBalance.add(penny);
                break;
            default:
                System.out.println("Unknown coin.");
        }
    }

    @Override
    public BigDecimal getBalance() {
        return userBalance;
    }

    @Override
    public String returnCoins() {
        //get formatted balance
        BigDecimal hundred = new BigDecimal(100);
        String formatUserBalance = userBalance.multiply(hundred).setScale(0, BigDecimal.ROUND_HALF_UP).toString();
        //convert BigDecimal toString

        //Parse String to int
        int userBalanceAsPennies = Integer.parseInt(formatUserBalance);

        int quarter = 0;
        int dime = 0;
        int nickel = 0;
        int penny = 0;

        while (userBalanceAsPennies > 0) {
            if (userBalanceAsPennies >= 25) {
                quarter++;
                userBalanceAsPennies -= 25;
            } else if (userBalanceAsPennies >= 10) {
                dime++;
                userBalanceAsPennies -= 10;
            } else if (userBalanceAsPennies >= 5) {
                nickel++;
                userBalanceAsPennies -= 5;
            } else {
                penny++;
                userBalanceAsPennies--;
            }
        }
        userBalance = new BigDecimal(0);
        return "Your change is " + quarter + " quarter(s), " + dime + " dime(s), " + nickel + " nickel(s), " + penny + " penny(s).";
    }

}
