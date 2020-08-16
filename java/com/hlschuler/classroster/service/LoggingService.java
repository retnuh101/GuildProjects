/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.classroster.service;

/**
 *
 * @author hschuler2992
 */
public interface LoggingService {
    public void log(String message);
    public void log(Exception exception);
}
