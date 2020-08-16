/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template orderFiles, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author hschuler2992
 */
public class OrderDAOImpl implements OrderDAO {

    public static final String TRAINING_FILE = "src/main/resources/Mode.txt";
    public static final String DELIMITER = ",";
    public static final String TRAINING_DELIMITER = "::";
    public static boolean isTraining = true;
    

    private Map<Integer, Order> orders = new HashMap<>();

    @Override
    public Order addOrder(int orderNumber, Order order)
            throws FlooringPersistenceException {
        Order newOrder = orders.put(orderNumber, order);
        return newOrder;
    }

    @Override
    public List<Order> getAllOrders()
            throws FlooringPersistenceException {
        return new ArrayList(orders.values());
    }
    
    @Override
    public List<Integer> getOrderNumberList()
            throws FlooringPersistenceException {
        return new ArrayList(orders.keySet());
    }

    @Override
    public Order getOrder(int orderNumber)
            throws FlooringPersistenceException {
        return orders.get(orderNumber);
    }

    @Override
    public Order editOrder(int orderNumber, Order toUpdate)
            throws FlooringPersistenceException {
        orders.put(orderNumber, toUpdate);
        return toUpdate;
    }

    @Override
    public Order removeOrder(int orderNumber)
            throws FlooringPersistenceException {
        Order removedOrder = orders.remove(orderNumber);
        return removedOrder;
    }
    
    @Override
    public Order unmarshallOrder(String orderAsText) 
            throws FlooringPersistenceException{
        String[] orderTokens = orderAsText.split(DELIMITER);

        Order orderFromFile = new Order();

        String orderNumberAsString = orderTokens[0];
        int orderNumber = Integer.parseInt(orderNumberAsString);
        orderFromFile.setOrderNumber(orderNumber);

        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);

        String taxRateAsString = orderTokens[3];
        BigDecimal taxRate = new BigDecimal(taxRateAsString);
        taxRate.toString();
        orderFromFile.setTaxRate(taxRate);

        orderFromFile.setProductType(orderTokens[4]);

        String areaAsString = orderTokens[5];
        BigDecimal area = new BigDecimal(areaAsString);
        area.toString();
        orderFromFile.setArea(area);

        String productCostAsString = orderTokens[6];
        BigDecimal productCost = new BigDecimal(productCostAsString);
        productCost.toString();
        orderFromFile.setCostPerSquareFoot(productCost);

        String laborCostAsString = orderTokens[7];
        BigDecimal laborCost = new BigDecimal(laborCostAsString);
        laborCost.toString();
        orderFromFile.setLaborCostPerSquareFoot(laborCost);

        String materialCostAsString = orderTokens[8];
        BigDecimal materialCost = new BigDecimal(materialCostAsString);
        materialCost.toString();
        orderFromFile.setMaterialCost(materialCost);

        String totalLaborCostAsString = orderTokens[9];
        BigDecimal totalLaborCost = new BigDecimal(totalLaborCostAsString);
        totalLaborCost.toString();
        orderFromFile.setLaborCost(totalLaborCost);

        String taxAsString = orderTokens[10];
        BigDecimal tax = new BigDecimal(taxAsString);
        tax.toString();
        orderFromFile.setTax(tax);

        String totalAsString = orderTokens[11];
        BigDecimal total = new BigDecimal(totalAsString);
        total.toString();
        orderFromFile.setTotalCost(total);
        
        String matPlusLabAsString = orderTokens[12];
        BigDecimal matPlusLab = new BigDecimal(matPlusLabAsString);
        matPlusLab.toString();
        orderFromFile.setMaterialPlusLabor(matPlusLab);

        return orderFromFile;
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws FlooringPersistenceException {

        List<Order> results = new ArrayList<>();
        for (Order o : orders.values()) {
            if (o.getDate().equals(date)) {
                //post it up right here
                results.add(o);
            } //FileNotFoundException
        }
        return results;
    }
    
    @Override
    public void loadAllFiles() throws FlooringPersistenceException {

        File orderFiles = new File("src/main/resources/orderfiles");

        if (orderFiles != null && orderFiles.exists()) {
            File[] listOfFiles = orderFiles.listFiles();

            if (listOfFiles != null) {

                for (File file : listOfFiles) {
                    if (file.isFile()) {
                        String fileToLoad = file.getPath();
                        loadFile(fileToLoad);
                    }
                }
            } 
        }
        //call loadTrainingMode
        loadTrainingMode();

    }
    
    
    private void loadTrainingMode() throws FlooringPersistenceException{
        
        Scanner scanner;

        try {
            
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TRAINING_FILE)));
        } catch (java.io.FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Could not load order data into memory.", e);
        }
        
            String currentLine = scanner.nextLine();
            unmarshallTrainingMode(currentLine);
        
        scanner.close();
        
    }
    
    private void unmarshallTrainingMode(String currentLine){
        String[] trainingIndexes = currentLine.split(TRAINING_DELIMITER);
        String trainingFromFile = trainingIndexes[1];
        if (trainingFromFile.equals("Training")){
            isTraining = true;
        } else {
            isTraining = false;
        }
    }
    
    public boolean getTrainingMode() throws FlooringPersistenceException{
        if (isTraining == true){
           return true; 
        } else {
            return false;
        }
    }

    private LocalDate getDateFromFileName(String fileName) {
        String dateAsString = fileName.substring(36, 44);
        String month = dateAsString.substring(0, 2);
        String day = dateAsString.substring(2, 4);
        String year = dateAsString.substring(4);
        LocalDate date = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        return date;
    }

    public void loadFile(String fileToLoad) throws FlooringPersistenceException {
        Scanner scanner;

        LocalDate date = getDateFromFileName(fileToLoad);

        try {
            // Create Scanner for reading the orderFiles
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(fileToLoad)));
        } catch (java.io.FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "Could not load order data into memory.", e);
        }
        String currentLine;
        Order currentOrder;
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrder = unmarshallOrder(currentLine);
            currentOrder.setDate(date);
            orders.put(currentOrder.getOrderNumber(), currentOrder);
        }
        scanner.close();
    }

    @Override
    public String marshallOrder(Order anOrder) throws FlooringPersistenceException {
        String orderAsText = "";
        orderAsText += anOrder.getOrderNumber() + DELIMITER;
        orderAsText += anOrder.getCustomerName() + DELIMITER;
        orderAsText += anOrder.getState() + DELIMITER;
        orderAsText += anOrder.getTaxRate() + DELIMITER;
        orderAsText += anOrder.getProductType() + DELIMITER;
        orderAsText += anOrder.getArea() + DELIMITER;
        orderAsText += anOrder.getCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getMaterialCost() + DELIMITER;
        orderAsText += anOrder.getLaborCost() + DELIMITER;
        orderAsText += anOrder.getTax() + DELIMITER;
        orderAsText += anOrder.getTotalCost() + DELIMITER;
        orderAsText += anOrder.getMaterialPlusLabor();
        return orderAsText;
    }

    public void writeOrder() throws FlooringPersistenceException {
        PrintWriter out = null;
        DateTimeFormatter formatted = DateTimeFormatter.ofPattern("MMddyyyy");

        List<Order> orderList = this.getAllOrders();
        List<LocalDate> dates = new ArrayList<>();
        for (Order o : orderList) {
            LocalDate nd = o.getDate();
            if (!dates.contains(nd)) {
                dates.add(nd);
            }
        }

        for (LocalDate d : dates) {

            String dateAsString = d.format(formatted);
            try {
                out = new PrintWriter(new FileWriter("src/main/resources/orderfiles/Order_" + dateAsString + ".txt"));
            } catch (IOException e) {
                throw new FlooringPersistenceException(
                        "Could not save order data.", e);
            }

            String orderAsText;
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                    + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,MaterialPlusLabor");

            for (Order o : orderList) {
                if (o.getDate().equals(d)) {
                    orderAsText = marshallOrder(o);
                    out.println(orderAsText);
                    out.flush();
                }
            }
        }
        out.close();
    }
 

    @Override
    public void saveCurrentWork() throws FlooringPersistenceException {
        if (isTraining == false){
            writeOrder();
        }
    }

}
