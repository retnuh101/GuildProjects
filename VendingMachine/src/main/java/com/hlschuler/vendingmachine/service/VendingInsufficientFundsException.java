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
public class VendingInsufficientFundsException extends Exception{
    public VendingInsufficientFundsException(String message){
        super(message);
    }
    public VendingInsufficientFundsException(String message, Throwable cause){
        super(message, cause);
    }
}
