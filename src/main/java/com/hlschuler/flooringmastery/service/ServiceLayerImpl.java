/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.service;

import com.hlschuler.flooringmastery.dao.FlooringPersistenceException;
import com.hlschuler.flooringmastery.dao.OrderDAO;
import com.hlschuler.flooringmastery.dao.OrderDAOImpl;
import com.hlschuler.flooringmastery.dao.ProductDAO;
import com.hlschuler.flooringmastery.dao.ProductDAOImpl;
import com.hlschuler.flooringmastery.dao.TaxDAO;
import com.hlschuler.flooringmastery.dao.TaxDAOImpl;
import com.hlschuler.flooringmastery.dto.Order;
import com.hlschuler.flooringmastery.dto.Product;
import com.hlschuler.flooringmastery.dto.Tax;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hschuler2992
 */
public class ServiceLayerImpl implements ServiceLayer{

    public OrderDAO orderDao;
    public ProductDAO productDao;
    public TaxDAO taxDao;
    
    private ServiceLayer service;
    
    public ServiceLayerImpl(ServiceLayer service){
        this.service = service;
    }
    
    public ServiceLayerImpl(OrderDAO orderDao, 
            ProductDAO productDao, 
            TaxDAO taxDao){
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public void loadAll() throws FlooringPersistenceException{
        orderDao.loadAllFiles();   
        productDao.loadProduct();
        taxDao.loadTax();
    }
    
    @Override
    public boolean getTrainingMode() throws FlooringPersistenceException{
         if (orderDao.getTrainingMode()){
             return true;
         } else {
             return false;
         }
     }
    
    @Override
    public List<Product> getAllProducts() throws FlooringPersistenceException{
        return productDao.getAllProducts();
    }
    
    @Override
    public List<Tax> getAllTax() throws FlooringPersistenceException{
        return taxDao.getAllTaxInfo();
    }
        
    
    @Override
    public void saveCurrentWork() throws FlooringPersistenceException{
        orderDao.saveCurrentWork();
    }
    
    @Override
    public Order addOrder(Order newOrder) throws 
            FlooringPersistenceException,
            OrderDataValidationException,
            OrderNumberDuplicationException{
        
        validateOrderData(newOrder);
        
        int orderNumber = incrementOrderNumber(newOrder);
        return orderDao.addOrder(orderNumber, newOrder);
    }
    
    public int incrementOrderNumber(Order newOrder) throws FlooringPersistenceException{
        //when adding new order, look through files to find greatest order number
        List<Integer> orderNumberList = orderDao.getOrderNumberList();
        int newOrderNumber = Collections.max(orderNumberList) + 1;
        newOrder.setOrderNumber(newOrderNumber);
        return newOrderNumber;
    }
       
    @Override
    public BigDecimal getSetMaterialCostPerSquareFoot(Order newOrder) {
        String productType = newOrder.getProductType();
        BigDecimal materialCostPerSquareFoot = productDao.getMaterialCost(productType);
        newOrder.setCostPerSquareFoot(materialCostPerSquareFoot);
        return materialCostPerSquareFoot;
    }
    
    @Override
    public BigDecimal calculateMaterialCost(Order newOrder, BigDecimal area, BigDecimal materialCostPerSquareFoot){
        BigDecimal materialCost = area.multiply(materialCostPerSquareFoot);
        newOrder.setMaterialCost(materialCost);
        return materialCost;
    }

    @Override
    public BigDecimal getSetLaborCostPerSquareFoot(Order newOrder) {
        String productType = newOrder.getProductType();
        BigDecimal laborCostPerSquareFoot = productDao.getLaborCost(productType);
        newOrder.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        return laborCostPerSquareFoot;
    }
    
    @Override
    public BigDecimal calculateLaborCost(Order newOrder, BigDecimal laborCostPerSquareFoot, BigDecimal area){
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
        newOrder.setLaborCost(laborCost);
        return laborCost;
    }
    
    @Override
    public BigDecimal calculateMaterialCostPlusLabor(Order newOrder, BigDecimal materialCost, BigDecimal laborCost){
        BigDecimal materialPlusLabor = materialCost.add(laborCost);
        newOrder.setMaterialPlusLabor(materialPlusLabor);
        return materialPlusLabor;
    }
    
    @Override
    public BigDecimal calculateTax(Order newOrder, BigDecimal materialCost, BigDecimal laborCost) {
        String state = newOrder.getState();
        BigDecimal taxRate = taxDao.getTaxRate(state);
        newOrder.setTaxRate(taxRate);
        BigDecimal materialPlusLabor = materialCost.add(laborCost);
        newOrder.setMaterialPlusLabor(materialPlusLabor);
        BigDecimal hundred = new BigDecimal("100");
        BigDecimal t = taxRate.divide(hundred);
        BigDecimal tax = materialPlusLabor.multiply(t);
        tax = tax.setScale(2, RoundingMode.HALF_UP);
        newOrder.setTax(tax);
        return tax;
    }
    
    @Override
    public BigDecimal calculateTotal(Order newOrder, BigDecimal materialPlusLabor, BigDecimal tax){
        BigDecimal total = materialPlusLabor.add(tax);
        total = total.setScale(2, RoundingMode.HALF_UP);
        newOrder.setTotalCost(total);
        return total;
    }

    @Override
    public List<Order> getOrdersForDate(LocalDate date) throws FlooringPersistenceException {
        return orderDao.getOrdersForDate(date);        
    }

    @Override
    public Order getOrder(int orderNumber) throws FlooringPersistenceException{
        return orderDao.getOrder(orderNumber);
    }

    @Override
    public Order editOrder(int orderNumber, Order toUpdate) throws 
            FlooringPersistenceException{
        return orderDao.editOrder(orderNumber, toUpdate);
    }

    @Override
    public Order removeOrder(int orderNumber) throws FlooringPersistenceException{
        return orderDao.removeOrder(orderNumber);
    }
    
    private void validateOrderData(Order order) throws
            OrderDataValidationException{
        if (order.getCustomerName() == null
                || order.getCustomerName().trim().length() == 0
                || order.getState() == null
                || order.getState().trim().length() == 0
                || order.getProductType() == null
                || order.getProductType().trim().length() == 0
                || order.getArea() == null
                || order.getArea().toString().trim().length() == 0){
            throw new OrderDataValidationException(
                    "Error: All fields [Customer Name, State, Product Type, Area] "
                            + "are required.");
        }
            
    }   
    
}
