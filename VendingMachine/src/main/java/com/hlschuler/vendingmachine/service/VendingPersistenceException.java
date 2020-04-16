/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.vendingmachine.service;

/**
 *
 * @author hschuler2992
 */
public class VendingPersistenceException extends Exception{
    public VendingPersistenceException(String message){
        super(message);
    }
    public VendingPersistenceException(String message, Throwable cause){
        super(message, cause);
    }
}
