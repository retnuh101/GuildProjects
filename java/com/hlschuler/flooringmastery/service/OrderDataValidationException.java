/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.flooringmastery.service;

/**
 *
 * @author hschuler2992
 */
public class OrderDataValidationException extends Exception{
    public OrderDataValidationException(String message){
        super(message);
    }
    public OrderDataValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
