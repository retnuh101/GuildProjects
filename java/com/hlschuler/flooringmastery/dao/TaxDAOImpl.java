/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author hschuler2992
 */
public class TaxDAOImpl implements TaxDAO {

    public static final String TEST_FILE = "testfile.txt";
    public static final String DELIMITER = ",";

    private Map<String, Tax> taxes = new HashMap<>();

    @Override
    public Tax getTaxInfo(String state) 
        throws FlooringPersistenceException {
        return taxes.get(state);
    }
    
    @Override
    public BigDecimal getTaxRate(String state){
        Tax tax = taxes.get(state);
        BigDecimal taxRate = tax.getTaxRate();
        return taxRate;
    }

    @Override
    public Tax addTaxInfo(String state, Tax tax) throws FlooringPersistenceException {
        Tax newTax = taxes.put(state, tax);
        return newTax;
    }

    @Override
    public List<Tax> getAllTaxInfo() throws FlooringPersistenceException {
        return new ArrayList(taxes.values());
    }

    @Override
    public Tax removeTaxInfo(String state) throws FlooringPersistenceException {
        Tax removedTax = taxes.remove(state);
        return removedTax;
    }

    private Tax unmarshallTax(String taxInfoAsText) {

        String[] taxTokens = taxInfoAsText.split(DELIMITER);
        //String stateAbbreviation = taxTokens[0];
        Tax taxInfoFromFile = new Tax();
        
        taxInfoFromFile.setStateAbbreviation(taxTokens[0]);
        
        taxInfoFromFile.setStateName(taxTokens[1]);

        String taxRateAsString = taxTokens[2];
        BigDecimal taxRate = new BigDecimal(taxRateAsString);
        taxRate.toString();
        taxInfoFromFile.setTaxRate(taxRate);

        return taxInfoFromFile;
    }
    
    @Override
    public void loadTax() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TEST_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Could not load tax data into memory.", e);
        }
        String currentLine;
        Tax currentTax;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);

            taxes.put(currentTax.getStateAbbreviation(), currentTax);
        }

        scanner.close();
    }

    

}
