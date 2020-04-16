/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.repos;

import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.EmployeeOfWeek;
import com.srmsolutions.entities.EmployeeOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author EricR
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeOfWeekRepositoryTest {
    
   @Autowired
   private EmployeeOfWeekRepository employeeOfWeeks;
   @Autowired
   private EmployeeRepository employees;
    
    @BeforeEach
    public  void setUp() {
        employeeOfWeeks.deleteAll();
        employees.deleteAll();
    }
    @org.junit.jupiter.api.Test
    public void testSaveOneGetAll() {
        EmployeeOfWeek employeeOfWeek = new EmployeeOfWeek();
        Employee employee = new Employee();
        employee = employees.save(employee);
        employeeOfWeek.setEmployee(employee);
        employeeOfWeek.setWeekStarting(LocalDate.MIN);
        employeeOfWeek.setDescription("Test Description");
        employeeOfWeeks.save(employeeOfWeek);
        List<EmployeeOfWeek> shouldHaveOne = employeeOfWeeks.findAll();
        Assertions.assertEquals(1, shouldHaveOne.size());
    }
//    @org.junit.jupiter.api.Test
//    public void testSaveManyGetAll() {
//        EmployeeOfWeek employeeOfWeek = new EmployeeOfWeek();
//        employeeOfWeek.setType("Test Type");
//        employeeOfWeek.setNumber("Test Number");
//       
//        EmployeeOfWeek employeeOfWeek2 = new EmployeeOfWeek();
//        employeeOfWeek2.setType("Test Type 2");
//        employeeOfWeek2.setNumber("Test Number 2");
//        
//        EmployeeOfWeek employeeOfWeek3 = new EmployeeOfWeek();
//        employeeOfWeek3.setType("Test Type 3");
//        employeeOfWeek3.setNumber("Test Number 3");
//        
//        employeeOfWeeks.save(employeeOfWeek);
//        employeeOfWeeks.save(employeeOfWeek2);
//        employeeOfWeeks.save(employeeOfWeek3);
//        
//        List<EmployeeOfWeek> shouldHaveThree = employeeOfWeeks.findAll();
//        Assertions.assertEquals(3, shouldHaveThree.size());
//    }
//    
//    @org.junit.jupiter.api.Test
//    public void testFindByID() {
//        EmployeeOfWeek employeeOfWeek = new EmployeeOfWeek();
//        employeeOfWeek.setType("Test Type");
//        employeeOfWeek.setNumber("Test Number");
//        
//        EmployeeOfWeek employeeOfWeek2 = new EmployeeOfWeek();
//        employeeOfWeek2.setType("Test Type 2");
//        employeeOfWeek2.setNumber("Test Number 2");
//        
//        EmployeeOfWeek employeeOfWeek3 = new EmployeeOfWeek();
//        employeeOfWeek3.setType("Test Type 3");
//        employeeOfWeek3.setNumber("Test Number 3");
//        
//        employeeOfWeeks.save(employeeOfWeek);
//        employeeOfWeeks.save(employeeOfWeek2);
//        employeeOfWeeks.save(employeeOfWeek3);
//        
//        EmployeeOfWeek shouldBeEmployeeOfWeek = employeeOfWeeks.findById(employeeOfWeek.getId()).orElse(null);
//        EmployeeOfWeek shouldBeEmployeeOfWeek2 = employeeOfWeeks.findById(employeeOfWeek2.getId()).orElse(null);
//        EmployeeOfWeek shouldBeEmployeeOfWeek3 = employeeOfWeeks.findById(employeeOfWeek3.getId()).orElse(null);
//        Assertions.assertEquals(employeeOfWeek, shouldBeEmployeeOfWeek);
//        Assertions.assertEquals(employeeOfWeek2, shouldBeEmployeeOfWeek2);
//        Assertions.assertEquals(employeeOfWeek3, shouldBeEmployeeOfWeek3);
//    }
//    @org.junit.jupiter.api.Test
//    public void testSavedUpdatesObject() {
//        EmployeeOfWeek employeeOfWeek = new EmployeeOfWeek();
//        employeeOfWeek.setType("Test Type");
//        employeeOfWeek.setNumber("Test Number");
//
//        employeeOfWeeks.save(employeeOfWeek);
//        EmployeeOfWeek shouldBe1 = employeeOfWeeks.findById(employeeOfWeek.getId()).orElse(null);
//        Assertions.assertEquals(employeeOfWeek, shouldBe1);
//        shouldBe1.setType("Test");
//        employeeOfWeeks.save(shouldBe1);
//        EmployeeOfWeek shouldBe1Modified = employeeOfWeeks.findById(employeeOfWeek.getId()).orElse(null);
//        long objectCount = employeeOfWeeks.count();
//        Assertions.assertEquals(shouldBe1Modified, shouldBe1);
//        Assertions.assertEquals(1, objectCount);
//    }
//    @org.junit.jupiter.api.Test
//    public void testDelete() {
//        EmployeeOfWeek employeeOfWeek = new EmployeeOfWeek();
//        employeeOfWeek.setType("Test Type");
//        employeeOfWeek.setNumber("Test Number");
//        
//        EmployeeOfWeek employeeOfWeek2 = new EmployeeOfWeek();
//        employeeOfWeek2.setType("Test Type 2");
//        employeeOfWeek2.setNumber("Test Number 2");
//        
//        EmployeeOfWeek employeeOfWeek3 = new EmployeeOfWeek();
//        employeeOfWeek3.setType("Test Type 3");
//        employeeOfWeek3.setNumber("Test Number 3");
//        
//        employeeOfWeeks.save(employeeOfWeek);
//        employeeOfWeeks.save(employeeOfWeek2);
//        employeeOfWeeks.save(employeeOfWeek3);
//        
//        long shouldBe3 = employeeOfWeeks.count();
//        employeeOfWeeks.delete(employeeOfWeek);
//        employeeOfWeeks.delete(employeeOfWeek2);
//        employeeOfWeeks.delete(employeeOfWeek3);
//        long shouldBe0 = employeeOfWeeks.count();
//        Assertions.assertEquals(0, shouldBe0);
//        Assertions.assertEquals(3, shouldBe3);
//        
//        EmployeeOfWeek employeeOfWeekNew = new EmployeeOfWeek();
//        employeeOfWeekNew.setType("Test New Type");
//        employeeOfWeekNew.setNumber("Test New Number");
//        employeeOfWeekNew = employeeOfWeeks.save(employeeOfWeekNew);
//        employeeOfWeeks.deleteById(employeeOfWeekNew.getId());
//        long shouldBe0Again = employeeOfWeeks.count();
//        Assertions.assertEquals(0, shouldBe0Again);
//    }
    
}
