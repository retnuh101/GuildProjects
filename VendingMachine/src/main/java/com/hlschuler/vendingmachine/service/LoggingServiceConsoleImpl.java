/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.vendingmachine.service;

import java.util.Date;

/**
 *
 * @author hschuler2992
 */
public class LoggingServiceConsoleImpl implements LoggingService{

    @Override
    public void log(String message) {
        Date now = new Date();
        System.out.println(now + ": " + message);
    }

    @Override
    public void log(Exception exception) {
        Date now = new Date();
        System.err.print(now);
    }
    
}
