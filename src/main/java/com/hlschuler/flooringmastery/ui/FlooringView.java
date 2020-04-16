/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.ui;

import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Product;
import com.hlschuler.flooringmastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public class FlooringView {

    private UserIO io;

    public FlooringView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print(" <<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public void displayNewOrderInfo(Order newOrder) {
        io.print("Customer Name: " + newOrder.getCustomerName());
        io.print("State: " + newOrder.getState());
        io.print("Product Type: " + newOrder.getProductType());
        io.print("Area: " + newOrder.getArea() + " SqFt.");
        io.print("Cost Per SqFt.: $" + newOrder.getCostPerSquareFoot());
        io.print("Labor Cost Per SqFt.: $" + newOrder.getLaborCostPerSquareFoot());
        io.print("Material Cost: $" + newOrder.getMaterialCost());
        io.print("Labor Cost: $" + newOrder.getLaborCost());
        io.print("Tax: $" + newOrder.getTax());
        io.print("Total Cost: $" + newOrder.getTotalCost());
    }

    public void displaySuccessfullyAddedOrderBanner() {
        io.print("===Order Successfully Added===");
        io.print("");
        io.readString("Please press enter to continue.");
    }

    public int displayAddOrderConfirmation() {
        io.print("Are you sure you want to place this order?");
        int confirmation = io.readInt("Press (1) for Yes. Press (2) for No.");
        return confirmation;
    }

    public void displayOrderList(List<Order> orderList) {
        for (Order currentOrder : orderList) {
            //Will print in line, could be stacked
            io.print(currentOrder.getOrderNumber() + ": "
                    + currentOrder.getCustomerName() + " - "
                    + currentOrder.getState() + " - "
                    + currentOrder.getProductType() + " - "
                    + currentOrder.getArea() + " SqFt. : "
                    + "$" + currentOrder.getTotalCost());
        }
        io.readString("Please hit enter to continue.");
    }

    public LocalDate displayDatePromptAndReturnDate() {
        int month = io.readInt("What month is your order? (mm)", 1, 12);
        int day = io.readInt("What day is your order? (dd)", 1, 31);
        int year = io.readInt("What year is your order? (yyyy)");
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(year, month, day);
        while (date.isBefore(now)) {
            year = io.readInt("What year is your order? (yyyy)");
        }
        return date;
    }

    public int displayOrderNumberPrompt(List<Order> orderList) {
        int orderNumber = io.readInt("What is the order number?");
        return orderNumber;
    }

    public int displayRemoveOrderConfirmation(int orderNumber) {

        io.print("Are you sure you want to remove Order #" + orderNumber + "?");
        int confirmation = io.readInt("Press (1) for Yes. Press (2) for No.", 1, 2);
        return confirmation;
    }

    public void displayEditOrderBanner() {
        io.print("===Edit Order===");
    }

    public Order displayGetNewOrderInfo(List<Product> products, List<Tax> taxes) {
        String customerName = io.readString("Please enter Customer Name: ");
        while (customerName.isBlank()) {
            customerName = io.readString("Cannot be blank. Please enter Customer Name");
        }
        String state = getValidTax(taxes);
        

        String productType = getValidProduct(products);

        Order currentOrder = new Order();
        BigDecimal area;
        BigDecimal hundred = new BigDecimal("100");
        boolean isValid = false;
        while (!isValid) {
            String areaAsString = io.readString("Please enter Area (min. 100 SqFt.)");
            if (!areaAsString.isBlank()) {

                area = new BigDecimal(areaAsString);

                if (area.compareTo(hundred) == -1) {
                    io.print("Please enter in area range (min. 100 SqFt.)");
                } else {
                    currentOrder.setArea(area);
                    isValid = true;
                }

            } else {
                io.print("Area cannot be blank!");
            }
        }

        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductType(productType);
        return currentOrder;
    }

    private String getValidProduct(List<Product> products) {
        List<String> productTypeList = new ArrayList<>();
        for (Product product : products){
            //add product type to list
            productTypeList.add(product.getProductType());
        }
        boolean validProduct = false;
        while (!validProduct) {
            String productType = io.readString("Please enter Product Type: ");
            //if (products.contains(productType)
            if (productTypeList.contains(productType)) {
                return productType;
            } else {
                io.print("Invalid product.");
            }
        }
        //should never run
        return null;
    }
    
    private String getValidTax(List<Tax> taxes){
        List<String> taxAbbList = new ArrayList<>();
        for (Tax tax : taxes){
            taxAbbList.add(tax.getStateAbbreviation());
        }
        boolean validTax = false;
        while (!validTax){
            String state = io.readString("Please enter State Abbreviation: ");
            if (taxAbbList.contains(state)){
                return state;
            } else {
                io.print("Invalid state.");
            }
        }
        return null;
    }

    public Order displayEditOrderInfo(int orderNumber, Order currentOrder) {
        BigDecimal hundred = new BigDecimal("100");
        String editedCustomerName = io.readString("Enter customer name (" + currentOrder.getCustomerName() + "): ");
        String editedStateAbbreviation = io.readString("Enter state abbreviation (" + currentOrder.getState() + "): ");
        String editedProductType = io.readString("Enter product type (" + currentOrder.getProductType() + "): ");
        String areaAsString = io.readString("Enter area (" + currentOrder.getArea() + "): ");
        Order toUpdate = new Order();

        if (areaAsString.isBlank()) {
            toUpdate.setArea(currentOrder.getArea());
        } else {
            BigDecimal editedArea = new BigDecimal(areaAsString);
            while (editedArea.compareTo(hundred) == -1) {
                areaAsString = io.readString("Area <100 SqFt. Please enter Area (min. 100 SqFt.)");
                editedArea = new BigDecimal(areaAsString);
            }
            toUpdate.setArea(editedArea);
        }

        if (editedCustomerName.isBlank()) {
            toUpdate.setCustomerName(currentOrder.getCustomerName());
        } else {
            toUpdate.setCustomerName(editedCustomerName);
        }

        if (editedStateAbbreviation.isBlank()) {
            toUpdate.setState(currentOrder.getState());
        } else {
            toUpdate.setState(editedStateAbbreviation);
        }

        if (editedProductType.isBlank()) {
            toUpdate.setProductType(currentOrder.getProductType());
        } else {
            toUpdate.setProductType(editedProductType);
        }

        return toUpdate;
    }

    public int displayEditOrderConfirmation(int orderNumber) {

        io.print("Are you sure you want to edit Order #" + orderNumber + "?");
        int confirmation = io.readInt("Press (1) for Yes. Press (2) for No.", 1, 2);
        return confirmation;
    }

    public int displayPromptSaveCurrentWork() {
        int confirmation = io.readInt("Save Current Work? Press (1) for Yes. Press (2) for No.", 1, 2);
        return confirmation;
    }

    public void displayTrainingModeBanner() {
        io.print("+++TRAINING MODE+++");
    }

    public void displayTrainingModeCannotSaveBanner() {
        io.readString("Training Mode. Cannot Save. Please hit enter to continue.");
    }

    public void displayWorkSuccessfullySavedBanner() {
        io.print("Work successfully saved to file.");
        io.readString("Please hit enter to continue.");
    }

    public void displayWorkNotSavedBanner() {
        io.print("Work not saved.");
    }

    public void displayRemoveOrderBanner() {
        io.print("===Remove Order===");
    }

    public void displayDisplayAllOrdersForDateBanner(LocalDate date) {
        io.print("===Display All Orders for " + date + "===");
    }

    public void displayAddOrderBanner() {
        io.print("===Add Order===");
    }

    public void displayExitBanner() {
        io.print("Good bye.");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
