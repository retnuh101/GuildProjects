/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.InventoryItem;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineFileImpl implements VendingMachineDao{

    private Map<String, InventoryItem> items;
    private final String DELIMITER = "::";
    private final String FILENAME;
    
    public VendingMachineFileImpl(){
        items = new HashMap<>();
        this.FILENAME = "inventory.txt";
    }
    
    public VendingMachineFileImpl(String fileName){
        this.FILENAME = fileName;
    }
    

    @Override
    public void loadAllItems() throws VendingPersistenceException{
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(FILENAME)));
        } catch (FileNotFoundException e) {
            throw new VendingPersistenceException(
                    "Could not load roster data into memory.", e);
        }
        
        String currentLine;
        InventoryItem currentItem;
        
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentItem = unmarshallItems(currentLine);
            
            items.put(currentItem.getItemId(), currentItem);
        }
        scanner.close();
    }
    
    public InventoryItem unmarshallItems(String itemAsText) {
        String[] itemTokens = itemAsText.split(DELIMITER);
        String itemId = itemTokens[0];
        
        InventoryItem itemFromFile = new InventoryItem(itemId);
        
        itemFromFile.setItemName(itemTokens[1]);
        
        //itemFromFile.setItemPrice(itemTokens[2]);
        
        String priceAsString = itemTokens[2];
        BigDecimal price = new BigDecimal(priceAsString);
        price.toString();
        itemFromFile.setItemPrice(price);
        
        String quantityAsString = itemTokens[3];
        int quantity = Integer.parseInt(quantityAsString);
        itemFromFile.setItemQuantity(quantity);
        
        
        return itemFromFile;
    }
    
    public String marshallItems (InventoryItem anInventoryItem){
        String itemAsText = anInventoryItem.getItemId() + DELIMITER;
        
        itemAsText += anInventoryItem.getItemName() + DELIMITER;
        
        itemAsText += anInventoryItem.getItemPrice() + DELIMITER;
        
        itemAsText += anInventoryItem.getItemQuantity();
        
        return itemAsText;
    }

    @Override
    public void saveAllChanges() throws VendingPersistenceException{
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(FILENAME));
        } catch (IOException e) {
            throw new VendingPersistenceException(
                    "Could not save student data.", e);
        }
        
        String itemAsText;
        List<InventoryItem> itemList = this.getAllItems();
        for (InventoryItem currentItem : itemList) {
            itemAsText = marshallItems(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }

    @Override
    public InventoryItem addItems(String itemId, InventoryItem toAdd) {
        return items.put(itemId, toAdd);
    }

    @Override
    public List<InventoryItem> getAllItems() {
        Collection<InventoryItem> allItems = items.values();
        List<InventoryItem> orderedItems = new ArrayList<>(allItems);
        return orderedItems;
    }

    @Override
    public InventoryItem getAnItem(String itemId) {
        InventoryItem storedUnderThatId = items.get(itemId);
        return storedUnderThatId;
    }

    @Override
    public void updateAnItem(String itemId, InventoryItem changedItem) {
        items.replace(itemId, changedItem);
    }

    @Override
    public InventoryItem removeAnItem(String itemId) {
        InventoryItem removed = items.remove(itemId);
        return removed;
    }

    
    
}
