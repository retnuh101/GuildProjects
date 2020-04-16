/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import dto.InventoryItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection(List<InventoryItem> itemList) {
        
        io.print("_/~\\_/~\\_/~\\_");
        io.print("Vending Options");
        io.print("_/~\\_/~\\_/~\\_");
        
        for (InventoryItem currentItem : itemList) {
            if (currentItem.getItemQuantity() > 0) {
                io.print(currentItem.getItemName() + ": $" + currentItem.getItemPrice());
            } else {
                io.print("No item in inventory.");
            }
        }
        
        io.print("1. Choose Option");
        
        io.print("2. Add Money");
        io.print("3. Return Money");
        
        io.print("4. Exit");
        
        return io.readInt("Please select from the above choices.", 1, 4);
    }
    
    public int printVendingOptionsGetSelection(List<InventoryItem> itemList) {
        
        for (InventoryItem currentItem : itemList) {
            if (currentItem.getItemQuantity() > 0) {
                io.print(currentItem.getItemName() + ": $" + currentItem.getItemPrice());
            } else {
                io.print("---SOLD OUT---");
            }
        } //else throw noiteminventory
        
        int inventoryMax = itemList.size();
        return io.readInt("Please select from the above choices.", 1, inventoryMax);
    }
    
    public int printCoinOptionsGetSelection() {
        io.print("Hit 1 to add Quarter");
        io.print("Hit 2 to add Dime");
        io.print("Hit 3 to add Nickel");
        io.print("Hit 4 to add Penny");
        
        return io.readInt("Please select from the above choices", 1, 4);
    }
    
    public void displayChooseOptionsBanner() {
        io.print("===CHOOSE OPTIONS===");
    }
    
    public void printUserBalance(BigDecimal userBalance) {
        String formatUserBalance = userBalance.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        io.print("User Balance: " + formatUserBalance);
    }
    
    public void printUserBalance(String userBalance) {
        io.print(userBalance);
    }
    
    public void printPurchasedItem(String purchasedItem) {
        io.print(purchasedItem);
    }
    
    public void displayItem(InventoryItem item) {
        if (item != null) {
            io.print(item.getItemId());
            io.print(item.getItemName() + " " + item.getItemPrice());
            io.print("");
        } else {
            io.print("No such item.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayAddMoneyBanner() {
        io.print("===ADD MONEY===");
    }
    
    public void displayExitBanner() {
        io.print("Good Bye!");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
}
