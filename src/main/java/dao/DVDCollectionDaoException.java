/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author hschuler2992
 */
public class DVDCollectionDaoException extends Exception{
    public DVDCollectionDaoException(String message){
        super(message);
    }
    public DVDCollectionDaoException(String message, Throwable cause){
        super(message, cause);
    }
}
