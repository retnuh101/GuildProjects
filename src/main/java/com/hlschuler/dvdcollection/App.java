/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.dvdcollection;

import com.hlschuler.dvdcollection.controller.DVDCollectionController;
import dao.DVDCollectionDao;
import dao.DVDCollectionDaoFileImpl;
import ui.DVDCollectionView;
import ui.UserIO;
import ui.UserIOConsoleImpl;

/**
 *
 * @author hschuler2992
 */
public class App {
    public static void main(String[] args) {
        UserIO myIo = new UserIOConsoleImpl();
        DVDCollectionView myView = new DVDCollectionView(myIo);
        DVDCollectionDao myDao = new DVDCollectionDaoFileImpl();
        DVDCollectionController controller = new DVDCollectionController(myDao, myView);
        controller.run();
    }
    
}
