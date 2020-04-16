/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.InventoryItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineMemImpl implements VendingMachineDao {

    private Map<String, InventoryItem> items;

    public VendingMachineMemImpl() {
        items = new HashMap<>();
    }

    @Override
    public void loadAllItems() {
        throw new UnsupportedOperationException("Not supported yet.");
        //unmarshall data from txt file
        /*
        Read the line into a string variable.
        Split the string into chunks at the :: delimiter.
        Create a new Student object.
        Use the first value from the split string to set the Student id.
        Use the second value from the split string to set the Student first name.
        Use the third value from the split string to set the Student last name.
        User the fourth value from the split string to set the cohort value.
        Put the newly created and initialized Student object into a collection or map.
        If there are more lines in the file, go to step a.
        If there are no more lines in the file, quit creating Student objects.
        Close the file.
         */

    }

    @Override
    public void saveAllChanges() {
        throw new UnsupportedOperationException("Not supported yet."); 
        /*
        marshall data from memory to txt file
        
        Go through the collection of students one by one.
        For each student, do the following:
        Create a string consisting of student id, first name, last name, and cohort (in that order), separated by ::
        Write the string to the output file.
        Get the next student (if one exists) and go back to step a.
        If there are no more students to process, quit.
        Close the file.
        */
    
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
