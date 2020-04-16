/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

import com.hlschuler.flooringmastery.dto.Order;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author hschuler2992
 */
public class OrderDAOTest {
    
    private OrderDAO dao = new OrderDAOImpl();
    
    public OrderDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        List<Order>orderList = dao.getAllOrders();
        for (Order order : orderList){
            dao.removeOrder(order.getOrderNumber());
        }
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class OrderDAO.
     */
    @Test
    public void testAddGetOrder() throws Exception{
        //Arrange        
        
        Order order = new Order();
        order.setOrderNumber(1);
        order.setCustomerName("Dwayne Johnson");
        order.setState("Florida");
        order.setProductType("Rock");
        BigDecimal area = new BigDecimal("100.00");
        order.setArea(area);
        //Act
        dao.addOrder(order.getOrderNumber(), order);
        
        Order fromDao = dao.getOrder(order.getOrderNumber()); 
        //Assert
        Assertions.assertEquals(order, fromDao);
    }

    /**
     * Test of getAllOrders method, of class OrderDAO.
     */
    @Test
    public void testGetAllOrders() throws Exception{
        
        //Arrange
        
        Order order1 = new Order();
        order1.setOrderNumber(1);
        order1.setCustomerName("Dwayne Johnson");
        order1.setState("Florida");
        order1.setProductType("Rock");
        BigDecimal area1 = new BigDecimal("100.00");
        order1.setArea(area1);
        
        Order order2 = new Order();
        order2.setOrderNumber(2);
        order2.setCustomerName("Betty White");
        order2.setState("Florida");
        order2.setProductType("Tile");
        BigDecimal area2 = new BigDecimal("300.00");
        order2.setArea(area2);
        
        //Act
        dao.addOrder(order1.getOrderNumber(), order1);
        dao.addOrder(order2.getOrderNumber(), order2);
        
        //Assert
        Assertions.assertEquals(2, dao.getAllOrders().size());
        
    }
    
    @Test
    public void testRemoveOrder() throws Exception{
        
        //Arrange
        
        Order order1 = new Order();
        order1.setOrderNumber(1);
        order1.setCustomerName("Dwayne Johnson");
        order1.setState("Florida");
        order1.setProductType("Rock");
        BigDecimal area1 = new BigDecimal("100.00");
        order1.setArea(area1);
        
        Order order2 = new Order();
        order2.setOrderNumber(2);
        order2.setCustomerName("Betty White");
        order2.setState("Florida");
        order2.setProductType("Tile");
        BigDecimal area2 = new BigDecimal("300.00");
        order2.setArea(area2);
        
        //Act
        dao.addOrder(order1.getOrderNumber(), order1);
        dao.addOrder(order2.getOrderNumber(), order2);
        
        //Assert
        dao.removeOrder(order1.getOrderNumber());
        Assertions.assertEquals(1, dao.getAllOrders().size());
        Assertions.assertNull(dao.getOrder(order1.getOrderNumber()));
        
        dao.removeOrder(order2.getOrderNumber());
        Assertions.assertEquals(0, dao.getAllOrders().size());
        Assertions.assertNull(dao.getOrder(order2.getOrderNumber()));
        
        
    }
    
    @Test
    public void testUnmarshallOrder() throws FlooringPersistenceException{
        //Arrange
        String orderLine = "1,Bob,IN,1.23,Tile,100,2.34,3.45,234,345,10,600,580";
        //Act
        Order fromLine = dao.unmarshallOrder(orderLine);
        //Assert
        
        Assertions.assertEquals(fromLine.getOrderNumber(), 1, "Order number should be 1.");
        Assertions.assertEquals(fromLine.getCustomerName(), "Bob", "Customer name should be Bob.");
        Assertions.assertEquals(fromLine.getState(), "IN", "State should be IN.");
        BigDecimal taxRate = new BigDecimal("1.23");
        Assertions.assertEquals(fromLine.getTaxRate(), taxRate, "Tax rate should be 1.23.");
        Assertions.assertEquals(fromLine.getProductType(), "Tile", "Product type should be tile.");
        BigDecimal area = new BigDecimal("100");
        Assertions.assertEquals(fromLine.getArea(), area, "Area should be 100.");
        BigDecimal cpsf = new BigDecimal("2.34");
        Assertions.assertEquals(fromLine.getCostPerSquareFoot(), cpsf, "CostPerSquareFoot should be 2.34.");
        BigDecimal lcpsf = new BigDecimal("3.45");
        Assertions.assertEquals(fromLine.getLaborCostPerSquareFoot(), lcpsf, "Labor cost per square foot should be 3.45.");
        BigDecimal matCost = new BigDecimal("234");
        Assertions.assertEquals(fromLine.getMaterialCost(), matCost, "Material cost should be 234.");
        BigDecimal labCost = new BigDecimal("345");
        Assertions.assertEquals(fromLine.getLaborCost(), labCost, "Labor cost should be 345.");
        BigDecimal tax = new BigDecimal("10");
        Assertions.assertEquals(fromLine.getTax(), tax, "Tax should be 10.");
        BigDecimal total = new BigDecimal("600");
        Assertions.assertEquals(fromLine.getTotalCost(), total, "Total should be 600.");
        BigDecimal matPlusLab = new BigDecimal("580");
        Assertions.assertEquals(fromLine.getMaterialPlusLabor(), matPlusLab, "M+L should be 580.");
    }
    
    @Test
    public void marshallOrder() throws FlooringPersistenceException{
        LocalDate date1 = LocalDate.of(2020, 1, 1);
        Order toText = new Order(date1, 1, "Erin", "IN", 
                new BigDecimal("3.14"), "Tile", new BigDecimal("100"), 
                new BigDecimal("2.19"), new BigDecimal("3.19"), new BigDecimal("300"),
                new BigDecimal("350"), new BigDecimal("380"), new BigDecimal("800"),
                new BigDecimal("800"));
        
        String orderAsText = dao.marshallOrder(toText);
        
        Assertions.assertEquals("1,Erin,IN,3.14,Tile,100,2.19,3.19,300,350,380,800,800", orderAsText, "Line should match.");
    }
}
