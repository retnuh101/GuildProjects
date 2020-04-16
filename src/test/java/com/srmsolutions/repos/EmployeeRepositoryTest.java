/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.repos;

import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.Event;
import com.srmsolutions.entities.JobRole;
import com.srmsolutions.entities.UserRole;
import java.time.LocalDate;
import java.util.ArrayList;
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
 * @author riddl
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTest {
    
    
    @Autowired
    private EmployeeRepository employees;
    @Autowired
    private UserRoleRepository userRoles;
    @Autowired
    private JobRoleRepository jobRoles;
    
    @BeforeEach
    public  void setUp() {
        employees.deleteAll();
        jobRoles.deleteAll();
        userRoles.deleteAll();
    }
    
    @org.junit.jupiter.api.Test
    public void testSaveOneGetAll() {
        Employee employee = new Employee();
        employee.setFirstName("Test First Name");
        employee.setLastName("Test Description");
        employee.setEmail("Test Email");
        employee.setPassword("Test Password");
        employee.setEmergencyContactName("Test Emergency Contact Name");
        employee.setEmergencyContactNumber("Test Emergency Contact Number");
        employee.setAddress("Test Address");
        employee.setHireDate(LocalDate.MIN);
        employee.setTermDate(LocalDate.MAX);
        employee.setHoursPto(1);
        employee.setSsn("Test SSN");
        employee.setManagerId(1);
        List<Event> eventList = new ArrayList<>();

        UserRole uRole = new UserRole();
        userRoles.save(uRole);
        employee.setUserRole(uRole);
        
        JobRole jRole = new JobRole();
        jobRoles.save(jRole);
        employee.setJobRole(jRole);
        
        employee.setGood(true);
        employee.setHr(true);
        employee.setAdmin(true);
//        employee.setImageUrl("Test image URL");
     
        employees.save(employee);
        List<Employee> shouldHaveOne = employees.findAll();
        Assertions.assertEquals(1, shouldHaveOne.size());
    }
    
    @org.junit.jupiter.api.Test
    public void testSaveManyGetAll() {
        Employee employee = new Employee();
        employee.setFirstName("Test First Name");
        employee.setLastName("Test Description");
        employee.setEmail("Test Email");
        employee.setPassword("Test Password");
        employee.setEmergencyContactName("Test Emergency Contact Name");
        employee.setEmergencyContactNumber("Test Emergency Contact Number");
        employee.setAddress("Test Address");
        employee.setHireDate(LocalDate.MIN);
        employee.setTermDate(LocalDate.MAX);
        employee.setHoursPto(1);
        employee.setSsn("Test SSN");
        employee.setManagerId(1);

        UserRole uRole = new UserRole();
        userRoles.save(uRole);
        employee.setUserRole(uRole);
        
        JobRole jRole = new JobRole();
        jobRoles.save(jRole);
        employee.setJobRole(jRole);
        
        employee.setGood(true);
        employee.setHr(true);
        employee.setAdmin(true);
//        employee.setImageUrl("Test image URL");
       
        Employee employee2 = new Employee();
        employee2.setFirstName("Test First Name 2");
        employee2.setLastName("Test Description 2");
        employee2.setEmail("Test Email 2");
        employee2.setPassword("Test Password 2");
        employee2.setEmergencyContactName("Test Emergency Contact Name 2");
        employee2.setEmergencyContactNumber("Test Emergency Contact Number 2");
        employee2.setAddress("Test Address 2");
        employee2.setHireDate(LocalDate.MIN);
        employee2.setTermDate(LocalDate.MAX);
        employee2.setHoursPto(2);
        employee2.setSsn("Test SSN 2");
        employee2.setManagerId(2);
        List<Event> eventList2 = new ArrayList<>();

        UserRole uRole2 = new UserRole();
        userRoles.save(uRole2);
        employee2.setUserRole(uRole2);
        
        JobRole jRole2 = new JobRole();
        jobRoles.save(jRole2);
        employee2.setJobRole(jRole2);
        
        employee2.setGood(true);
        employee2.setHr(true);
        employee2.setAdmin(true);
//        employee2.setImageUrl("Test image URL 2");
        
        Employee employee3 = new Employee();
        employee3.setFirstName("Test First Name 2");
        employee3.setLastName("Test Description 2");
        employee3.setEmail("Test Email 2");
        employee3.setPassword("Test Password 2");
        employee3.setEmergencyContactName("Test Emergency Contact Name 2");
        employee3.setEmergencyContactNumber("Test Emergency Contact Number 2");
        employee3.setAddress("Test Address 2");
        employee3.setHireDate(LocalDate.MIN);
        employee3.setTermDate(LocalDate.MAX);
        employee3.setHoursPto(3);
        employee3.setSsn("Test SSN 2");
        employee3.setManagerId(3);
        List<Event> eventList3 = new ArrayList<>();

        UserRole uRole3 = new UserRole();
        userRoles.save(uRole3);
        employee3.setUserRole(uRole3);
        
        JobRole jRole3 = new JobRole();
        jobRoles.save(jRole3);
        employee3.setJobRole(jRole3);
        
        employee3.setGood(true);
        employee3.setHr(true);
        employee3.setAdmin(true);
//        employee3.setImageUrl("Test image URL 3");
        
        employees.save(employee);
        employees.save(employee2);
        employees.save(employee3);
        
        List<Employee> shouldHaveThree = employees.findAll();
        Assertions.assertEquals(3, shouldHaveThree.size());
    }
    //@org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Test
    public void testFindByID() {
        Employee employee = new Employee();
        employee.setFirstName("Test First Name");
        employee.setLastName("Test Description");
        employee.setEmail("Test Email");
        employee.setPassword("Test Password");
        employee.setEmergencyContactName("Test Emergency Contact Name");
        employee.setEmergencyContactNumber("Test Emergency Contact Number");
        employee.setAddress("Test Address");
        employee.setHireDate(LocalDate.MIN);
        employee.setTermDate(LocalDate.MAX);
        employee.setHoursPto(1);
        employee.setSsn("Test SSN");
        employee.setManagerId(1);
        List<Event> eventList = new ArrayList<>();

        UserRole uRole = new UserRole();
        userRoles.save(uRole);
        employee.setUserRole(uRole);
        
        JobRole jRole = new JobRole();
        jobRoles.save(jRole);
        employee.setJobRole(jRole);
        
        employee.setGood(true);
        employee.setHr(true);
        employee.setAdmin(true);
//        employee.setImageUrl("Test image URL");
       
        Employee employee2 = new Employee();
        employee2.setFirstName("Test First Name 2");
        employee2.setLastName("Test Description 2");
        employee2.setEmail("Test Email 2");
        employee2.setPassword("Test Password 2");
        employee2.setEmergencyContactName("Test Emergency Contact Name 2");
        employee2.setEmergencyContactNumber("Test Emergency Contact Number 2");
        employee2.setAddress("Test Address 2");
        employee2.setHireDate(LocalDate.MIN);
        employee2.setTermDate(LocalDate.MAX);
        employee2.setHoursPto(2);
        employee2.setSsn("Test SSN 2");
        employee2.setManagerId(2);
        List<Event> eventList2 = new ArrayList<>();

        UserRole uRole2 = new UserRole();
        userRoles.save(uRole2);
        employee2.setUserRole(uRole2);
        
        JobRole jRole2 = new JobRole();
        jobRoles.save(jRole2);
        employee2.setJobRole(jRole2);
        
        employee2.setGood(true);
        employee2.setHr(true);
        employee2.setAdmin(true);
//        employee2.setImageUrl("Test image URL 2");
        
        Employee employee3 = new Employee();
        employee3.setFirstName("Test First Name 3");
        employee3.setLastName("Test Description 3");
        employee3.setEmail("Test Email 3");
        employee3.setPassword("Test Password 3");
        employee3.setEmergencyContactName("Test Emergency Contact Name 3");
        employee3.setEmergencyContactNumber("Test Emergency Contact Number 3");
        employee3.setAddress("Test Address 3");
        employee3.setHireDate(LocalDate.MIN);
        employee3.setTermDate(LocalDate.MAX);
        employee3.setHoursPto(3);
        employee3.setSsn("Test SSN 3");
        employee3.setManagerId(3);
        List<Event> eventList3 = new ArrayList<>();

        UserRole uRole3 = new UserRole();
        userRoles.save(uRole3);
        employee3.setUserRole(uRole3);
        
        JobRole jRole3 = new JobRole();
        jobRoles.save(jRole3);
        employee3.setJobRole(jRole3);
        
        employee3.setGood(true);
        employee3.setHr(true);
        employee3.setAdmin(true);
//        employee3.setImageUrl("Test image URL 3");
        
        employees.save(employee);
        employees.save(employee2);
        employees.save(employee3);
        
        Employee shouldBeEmployee = employees.findById(employee.getId()).orElse(null);
        Employee shouldBeEmployee2 = employees.findById(employee2.getId()).orElse(null);
        Employee shouldBeEmployee3 = employees.findById(employee3.getId()).orElse(null);
        Assertions.assertEquals(employee, shouldBeEmployee);
        Assertions.assertEquals(employee2, shouldBeEmployee2);
        Assertions.assertEquals(employee3, shouldBeEmployee3);
    }
    @org.junit.jupiter.api.Test
    public void testSavedUpdatesObject() {
        Employee employee = new Employee();
        employee.setFirstName("Test First Name");
        employee.setLastName("Test Description");
        employee.setEmail("Test Email");
        employee.setPassword("Test Password");
        employee.setEmergencyContactName("Test Emergency Contact Name");
        employee.setEmergencyContactNumber("Test Emergency Contact Number");
        employee.setAddress("Test Address");
        employee.setHireDate(LocalDate.MIN);
        employee.setTermDate(LocalDate.MAX);
        employee.setHoursPto(1);
        employee.setSsn("Test SSN");
        employee.setManagerId(1);
        List<Event> eventList = new ArrayList<>();

        UserRole uRole = new UserRole();
        userRoles.save(uRole);
        employee.setUserRole(uRole);
        
        JobRole jRole = new JobRole();
        jobRoles.save(jRole);
        employee.setJobRole(jRole);
        
        employee.setGood(true);
        employee.setHr(true);
        employee.setAdmin(true);
//        employee.setImageUrl("Test image URL");

        employees.save(employee);
        Employee shouldBe1 = employees.findById(employee.getId()).orElse(null);
        Assertions.assertEquals(employee, shouldBe1);
        shouldBe1.setFirstName("Test");
        employees.save(shouldBe1);
        Employee shouldBe1Modified = employees.findById(employee.getId()).orElse(null);
        long objectCount = employees.count();
        Assertions.assertEquals(shouldBe1Modified, shouldBe1);
        Assertions.assertEquals(1, objectCount);
    }
    @org.junit.jupiter.api.Test
    public void testDelete() {
        Employee employee = new Employee();
        employee.setFirstName("Test First Name");
        employee.setLastName("Test Description");
        employee.setEmail("Test Email");
        employee.setPassword("Test Password");
        employee.setEmergencyContactName("Test Emergency Contact Name");
        employee.setEmergencyContactNumber("Test Emergency Contact Number");
        employee.setAddress("Test Address");
        employee.setHireDate(LocalDate.MIN);
        employee.setTermDate(LocalDate.MAX);
        employee.setHoursPto(1);
        employee.setSsn("Test SSN");
        employee.setManagerId(1);
        List<Event> eventList = new ArrayList<>();

        UserRole uRole = new UserRole();
        userRoles.save(uRole);
        employee.setUserRole(uRole);
        
        JobRole jRole = new JobRole();
        jobRoles.save(jRole);
        employee.setJobRole(jRole);
        
        employee.setGood(true);
        employee.setHr(true);
        employee.setAdmin(true);
//        employee.setImageUrl("Test image URL");
       
        Employee employee2 = new Employee();
        employee2.setFirstName("Test First Name 2");
        employee2.setLastName("Test Description 2");
        employee2.setEmail("Test Email 2");
        employee2.setPassword("Test Password 2");
        employee2.setEmergencyContactName("Test Emergency Contact Name 2");
        employee2.setEmergencyContactNumber("Test Emergency Contact Number 2");
        employee2.setAddress("Test Address 2");
        employee2.setHireDate(LocalDate.MIN);
        employee2.setTermDate(LocalDate.MAX);
        employee2.setHoursPto(2);
        employee2.setSsn("Test SSN 2");
        employee2.setManagerId(2);
        List<Event> eventList2 = new ArrayList<>();

        UserRole uRole2 = new UserRole();
        userRoles.save(uRole2);
        employee2.setUserRole(uRole2);
        
        JobRole jRole2 = new JobRole();
        jobRoles.save(jRole2);
        employee2.setJobRole(jRole2);
        
        employee2.setGood(true);
        employee2.setHr(true);
        employee2.setAdmin(true);
//        employee2.setImageUrl("Test image URL 2");
        
        Employee employee3 = new Employee();
        employee3.setFirstName("Test First Name 3");
        employee3.setLastName("Test Description 3");
        employee3.setEmail("Test Email 3");
        employee3.setPassword("Test Password 3");
        employee3.setEmergencyContactName("Test Emergency Contact Name 3");
        employee3.setEmergencyContactNumber("Test Emergency Contact Number 3");
        employee3.setAddress("Test Address 3");
        employee3.setHireDate(LocalDate.MIN);
        employee3.setTermDate(LocalDate.MAX);
        employee3.setHoursPto(3);
        employee3.setSsn("Test SSN 3");
        employee3.setManagerId(3);
        List<Event> eventList3 = new ArrayList<>();

        UserRole uRole3 = new UserRole();
        userRoles.save(uRole3);
        employee3.setUserRole(uRole3);
        
        JobRole jRole3 = new JobRole();
        jobRoles.save(jRole3);
        employee3.setJobRole(jRole3);
        
        employee3.setGood(true);
        employee3.setHr(true);
        employee3.setAdmin(true);
//        employee3.setImageUrl("Test image URL 3");
        
        employees.save(employee);
        employees.save(employee2);
        employees.save(employee3);
        
        long shouldBe3 = employees.count();
        employees.delete(employee);
        employees.delete(employee2);
        employees.delete(employee3);
        long shouldBe0 = employees.count();
        Assertions.assertEquals(0, shouldBe0);
        Assertions.assertEquals(3, shouldBe3);
        
        Employee employeeNew = new Employee();
        employeeNew.setFirstName("Test New First Name");
        employeeNew.setLastName("Test New Last Name");
        employeeNew = employees.save(employeeNew);
        employees.deleteById(employeeNew.getId());
        long shouldBe0Again = employees.count();
        Assertions.assertEquals(0, shouldBe0Again);
    }
}
