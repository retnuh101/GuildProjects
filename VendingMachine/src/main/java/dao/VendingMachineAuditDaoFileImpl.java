/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author hschuler2992
 */
public class VendingMachineAuditDaoFileImpl implements VendingMachineAuditDao{

    public static final String AUDIT_FILE = "audit.txt";
    
    @Override
    public void writeAuditEntry(String entry) throws VendingPersistenceException {
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
//            throws new VendingPersistenceException("Could not persist audit info.", e);
        }
        
    }
    
}
