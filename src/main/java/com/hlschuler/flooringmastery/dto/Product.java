/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author hschuler2992
 */
public class Product {
    
    private String productType;
    private BigDecimal costSquareFoot;
    private BigDecimal laborCostSquareFoot;
    
    //Constructor
    public Product(){}

    public Product(String productType, BigDecimal costSquareFoot, BigDecimal laborCostSquareFoot) {
        this.productType = productType;
        this.costSquareFoot = costSquareFoot;
        this.laborCostSquareFoot = laborCostSquareFoot;
    }
    
    //Getters & Setters

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getCostSquareFoot() {
        return costSquareFoot;
    }

    public void setCostSquareFoot(BigDecimal costSquareFoot) {
        this.costSquareFoot = costSquareFoot;
    }

    public BigDecimal getLaborCostSquareFoot() {
        return laborCostSquareFoot;
    }

    public void setLaborCostSquareFoot(BigDecimal laborCostSquareFoot) {
        this.laborCostSquareFoot = laborCostSquareFoot;
    }
    
    
}
