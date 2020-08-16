/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.dao;

/**
 *
 * @author hschuler2992
 */
public class TaxInfoNotFoundException extends Exception{
    
    public TaxInfoNotFoundException(String message){
        super(message);
    }
    public TaxInfoNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
    
}
