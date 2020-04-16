/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.vendingmachine.controller;

import com.hlschuler.vendingmachine.Coins;
import com.hlschuler.vendingmachine.service.LoggingService;
import com.hlschuler.vendingmachine.service.VendingInsufficientFundsException;
import com.hlschuler.vendingmachine.service.VendingNoItemInventoryException;
import com.hlschuler.vendingmachine.service.VendingPersistenceException;
import com.hlschuler.vendingmachine.service.VendingServiceLayer;
import dao.VendingMachineDao;
import dto.InventoryItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.UserIO;
import ui.UserIOConsoleImpl;
import ui.VendingMachineView;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineController {
    private LoggingService log;
    
    VendingMachineView view;
    private VendingServiceLayer service;
    private UserIO io = new UserIOConsoleImpl();
    VendingMachineDao dao;
    
    public VendingMachineController(VendingServiceLayer service, VendingMachineView view, 
            VendingMachineDao dao, LoggingService log){
        this.service = service;
        this.view = view;
        this.dao = dao;
        this.log = log;
    }

    public void run() {
        
        
        try {
            dao.loadAllItems();
        } catch (dao.VendingPersistenceException ex) {
            io.print("Could not load inventory.");
            return;
        }
        boolean keepGoing = true;
        int menuSelection = 0;
        
        while (keepGoing) {
                        
            menuSelection = getMenuSelection();
            
            switch (menuSelection){
                case 1:
                    chooseOption(); 
                    break;
                case 2:
                    addMoney();
                    BigDecimal userBalance = service.getBalance();
                    view.printUserBalance(userBalance);
                    break;
                case 3:
                    returnMoney();
                    break;
                case 4:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }
        }
        try {
            dao.saveAllChanges();
        } catch (dao.VendingPersistenceException ex) {
            io.print("Could not save to inventory.");
            return;
        }
        exitMessage();
        log.log("System shut down.");
        
    }
    private int getMenuSelection(){
        List<InventoryItem> itemList = dao.getAllItems();
        return view.printMenuAndGetSelection(itemList);
    }
    
    private void chooseOption(){
        List<InventoryItem> itemList = dao.getAllItems();
        int vendingSelection =  view.printVendingOptionsGetSelection(itemList);
        
        
        itemSelection(vendingSelection);
        
    }
    
    public void itemSelection(int vendingSelection) {
        try {
            String purchasedItem = service.purchaseItem(vendingSelection);
            view.printPurchasedItem(purchasedItem); 
        } catch (VendingPersistenceException ex) {
            //TODO Handle Exception
            
        } catch (VendingInsufficientFundsException ex) {
            //            TODO Handle Exception
            io.print("Insufficient Funds!");
        } catch (VendingNoItemInventoryException ex) {
//            TODO Handle Exception
            io.print("No item in inventory!");
        }
        
    }
    
    private void addMoney(){
        view.displayAddMoneyBanner();
        int coinSelection = view.printCoinOptionsGetSelection();
        switch(coinSelection){
            //1 = quarter
            case 1:
                service.addCoin(Coins.QUARTERS);
                break;
            //2 = dime
            case 2:
                service.addCoin(Coins.DIMES);
                break;
            //3 = nickel
            case 3:
                service.addCoin(Coins.NICKELS);
                break;
            //4 = penny
            case 4:
                service.addCoin(Coins.PENNIES);
                break;
            default:
                System.out.println("Money not added.");
        }
        
        //return to main menu and display current balance
    }
    
    
    
    private void returnMoney(){
        String userBalance = service.returnCoins();
        view.printUserBalance(userBalance);
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
    
    
}
