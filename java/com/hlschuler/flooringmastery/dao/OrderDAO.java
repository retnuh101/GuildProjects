/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Order;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public interface OrderDAO {
    
    Order addOrder(int orderNumber, Order order) throws FlooringPersistenceException;
    
    List<Order> getAllOrders() throws FlooringPersistenceException;
    
    Order getOrder(int orderNumber) throws FlooringPersistenceException;
    
    Order editOrder(int orderNumber, Order toUpdate) throws FlooringPersistenceException;
    
    Order removeOrder(int orderNumber) throws FlooringPersistenceException;
    
    public List<Order> getOrdersForDate(LocalDate date) throws FlooringPersistenceException;
    
    public Order unmarshallOrder(String orderAsText) throws FlooringPersistenceException;
    
    public void loadAllFiles() throws FlooringPersistenceException;
    
    public List<Integer> getOrderNumberList()
            throws FlooringPersistenceException;
    
    public void saveCurrentWork() throws FlooringPersistenceException;
    
    public String marshallOrder(Order anOrder) throws FlooringPersistenceException;
    
     public boolean getTrainingMode() throws FlooringPersistenceException;
}
