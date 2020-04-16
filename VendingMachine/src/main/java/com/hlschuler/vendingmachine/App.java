/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.vendingmachine;

import com.hlschuler.vendingmachine.controller.VendingMachineController;
import com.hlschuler.vendingmachine.service.LoggingService;
import com.hlschuler.vendingmachine.service.LoggingServiceConsoleImpl;
import com.hlschuler.vendingmachine.service.VendingServiceLayer;
import com.hlschuler.vendingmachine.service.VendingServiceLayerImpl;
import dao.VendingMachineAuditDao;
import dao.VendingMachineAuditDaoFileImpl;
import dao.VendingMachineDao;
import dao.VendingMachineFileImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ui.UserIO;
import ui.UserIOConsoleImpl;
import ui.VendingMachineView;

/**
 *
 * @author hschuler2992
 */
public class App {
    public static void main(String[] args) {
//        UserIO myIo = new UserIOConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(myIo);
//        VendingMachineDao myDao = new VendingMachineFileImpl();
//        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
//        VendingServiceLayer myService = new VendingServiceLayerImpl(myDao, myAuditDao);
//        LoggingService myLog = new LoggingServiceConsoleImpl();
//        VendingMachineController controller = new VendingMachineController(myService, myView, myDao, myLog);
//        controller.run();      

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
        
    }
    
}
