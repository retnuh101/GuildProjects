/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.controller;

import com.hlschuler.flooringmastery.dao.FlooringPersistenceException;
import com.hlschuler.flooringmastery.dao.OrderDAO;
import com.hlschuler.flooringmastery.dao.OrderDAOImpl;
import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Product;
import com.hlschuler.flooringmastery.service.OrderDataValidationException;
import com.hlschuler.flooringmastery.service.OrderNumberDuplicationException;
import com.hlschuler.flooringmastery.service.ServiceLayer;
import com.hlschuler.flooringmastery.service.ServiceLayerImpl;
import com.hlschuler.flooringmastery.ui.FlooringView;
import com.hlschuler.flooringmastery.ui.UserIO;
import com.hlschuler.flooringmastery.ui.UserIOConsoleImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hschuler2992
 */
public class FlooringController {

    private ServiceLayer service;

    FlooringView view;

    public FlooringController(FlooringView view, ServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    private UserIO io = new UserIOConsoleImpl();

    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        try {
            service.loadAll();
            
            while (keepGoing) {
                if (service.getTrainingMode()){
                    view.displayTrainingModeBanner();
                }
                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        listAllOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        saveCurrentWork();
                        break;
                    case 6:
                        exitPrompt();
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            exitMessage();
        } catch (FlooringPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() throws FlooringPersistenceException {
        view.displayAddOrderBanner();

        LocalDate date = view.displayDatePromptAndReturnDate();

        Order newOrder = view.displayGetNewOrderInfo(service.getAllProducts(), service.getAllTax());
        newOrder.setDate(date);
        //pass newOrder to service layer to calculate material cost
        newOrder.setCostPerSquareFoot(service.getSetMaterialCostPerSquareFoot(newOrder));
        newOrder.setMaterialCost(service.calculateMaterialCost(newOrder, newOrder.getArea(), newOrder.getCostPerSquareFoot()));
        //pass newOrder to service layer to calculate labor cost
        newOrder.setLaborCostPerSquareFoot(service.getSetLaborCostPerSquareFoot(newOrder));
        newOrder.setLaborCost(service.calculateLaborCost(newOrder, newOrder.getLaborCostPerSquareFoot(), newOrder.getArea()));
        //pass newOrder to service layer to calculate tax
        newOrder.setTax(service.calculateTax(newOrder, newOrder.getMaterialCost(), newOrder.getLaborCost()));
        //bring back newOrder with additions and send to view for confirmation
        newOrder.setTotalCost(service.calculateTotal(newOrder, newOrder.getMaterialPlusLabor(), newOrder.getTax()));
        newOrder.setMaterialPlusLabor(service.calculateMaterialCostPlusLabor(newOrder, newOrder.getMaterialCost(), newOrder.getLaborCost()));
        view.displayNewOrderInfo(newOrder);
        int confirmation = view.displayAddOrderConfirmation();

        switch (confirmation) {
            case 1: {
                try {
                    service.addOrder(newOrder);
                } catch (OrderDataValidationException ex) {
                    Logger.getLogger(FlooringController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OrderNumberDuplicationException ex) {
                    Logger.getLogger(FlooringController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            view.displaySuccessfullyAddedOrderBanner();
            break;

            case 2:
                break;
            default:
                unknownCommand();
        }

    }

    private void removeOrder() throws FlooringPersistenceException {
        view.displayRemoveOrderBanner();
        LocalDate date = view.displayDatePromptAndReturnDate();
        List<Order> orderList = service.getOrdersForDate(date);
        int orderNumber = view.displayOrderNumberPrompt(orderList);
        int confirmation = view.displayRemoveOrderConfirmation(orderNumber);

        switch (confirmation) {
            case 1:
                service.removeOrder(orderNumber);
                break;
            case 2:
                break;
            default:
                unknownCommand();
        }
    }

    private void editOrder() throws FlooringPersistenceException {
        view.displayEditOrderBanner();
        LocalDate date = view.displayDatePromptAndReturnDate();
        List<Order> orderList = service.getOrdersForDate(date);
        int orderNumber = view.displayOrderNumberPrompt(orderList);

        Order currentOrder = service.getOrder(orderNumber);
        Order toUpdate = view.displayEditOrderInfo(orderNumber, currentOrder);

        toUpdate.setDate(date);
        toUpdate.setOrderNumber(orderNumber);
        toUpdate.setCostPerSquareFoot(service.getSetMaterialCostPerSquareFoot(toUpdate));
        toUpdate.setMaterialCost(service.calculateMaterialCost(toUpdate, toUpdate.getArea(), toUpdate.getCostPerSquareFoot()));
        //pass newOrder to service layer to calculate labor cost
        toUpdate.setLaborCostPerSquareFoot(service.getSetLaborCostPerSquareFoot(toUpdate));
        toUpdate.setLaborCost(service.calculateLaborCost(toUpdate, toUpdate.getLaborCostPerSquareFoot(), toUpdate.getArea()));
        //pass newOrder to service layer to calculate tax
        toUpdate.setTax(service.calculateTax(toUpdate, toUpdate.getMaterialCost(), toUpdate.getLaborCost()));
        //bring back newOrder with additions and send to view for confirmation
        toUpdate.setTotalCost(service.calculateTotal(toUpdate, toUpdate.getMaterialPlusLabor(), toUpdate.getTax()));
        toUpdate.setMaterialPlusLabor(service.calculateMaterialCostPlusLabor(toUpdate, toUpdate.getMaterialCost(), toUpdate.getLaborCost()));
        view.displayNewOrderInfo(toUpdate);
        int confirmation = view.displayEditOrderConfirmation(orderNumber);
        switch (confirmation) {
            case 1:
                service.editOrder(orderNumber, toUpdate);
                break;
            case 2:
                break;
            default:
                unknownCommand();
        }
    }
    
    

    private void saveCurrentWork() throws FlooringPersistenceException {
        //if (isTraining = false)
        if (!service.getTrainingMode()) {
            int confirmation = view.displayPromptSaveCurrentWork();

            switch (confirmation) {
                case 1:
                    service.saveCurrentWork();
                    view.displayWorkSuccessfullySavedBanner();
                    break;
                case 2:
                    break;
                default:
                    unknownCommand();
            }
        } else {
            view.displayTrainingModeCannotSaveBanner();
        }
    }

    private void listAllOrders() throws FlooringPersistenceException {
        LocalDate date = view.displayDatePromptAndReturnDate();
        List<Order> orderList = service.getOrdersForDate(date);
        view.displayDisplayAllOrdersForDateBanner(date);
        view.displayOrderList(orderList);
    }

    private int getMenuSelection() throws FlooringPersistenceException {
        return view.printMenuAndGetSelection();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitPrompt() throws FlooringPersistenceException {
        if (!service.getTrainingMode()){
        
        int confirmation = view.displayPromptSaveCurrentWork();

        switch (confirmation) {
            case 1:
                service.saveCurrentWork();
                view.displayWorkSuccessfullySavedBanner();
                break;
            case 2:
                view.displayWorkNotSavedBanner();
                break;
            default:
                unknownCommand();
        }
        } 
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
