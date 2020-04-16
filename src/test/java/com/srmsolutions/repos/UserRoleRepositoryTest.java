/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.repos;

import com.srmsolutions.entities.UserRole;
import java.util.List;
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
public class UserRoleRepositoryTest {
    
    @Autowired
    private UserRoleRepository userRoles;
    
    @BeforeEach
    public  void setUp() {
        userRoles.deleteAll();
    }
    @org.junit.jupiter.api.Test
    public void testSaveOneGetAll() {
        UserRole uRole = new UserRole();
        uRole.setName("Test Name");
        uRole.setDescription("Test Description");
        userRoles.save(uRole);
        List<UserRole> shouldHaveOne = userRoles.findAll();
        Assertions.assertEquals(1, shouldHaveOne.size());
    }
    @org.junit.jupiter.api.Test
    public void testSaveManyGetAll() {
        UserRole uRole = new UserRole();
        uRole.setName("Test Name");
        uRole.setDescription("Test Description");
       
        UserRole uRole2 = new UserRole();
        uRole2.setName("Test Name 2");
        uRole2.setDescription("Test Description 2");
        
        UserRole uRole3 = new UserRole();
        uRole3.setName("Test Name 3");
        uRole3.setDescription("Test Description 3");
        
        userRoles.save(uRole);
        userRoles.save(uRole2);
        userRoles.save(uRole3);
        
        List<UserRole> shouldHaveThree = userRoles.findAll();
        Assertions.assertEquals(3, shouldHaveThree.size());
    }
    //@org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Test
    public void testFindByID() {
        UserRole uRole = new UserRole();
        uRole.setName("Test Name");
        uRole.setDescription("Test Description");
        
        UserRole uRole2 = new UserRole();
        uRole2.setName("Test Name 2");
        uRole2.setDescription("Test Description 2");
        
        UserRole uRole3 = new UserRole();
        uRole3.setName("Test Name 3");
        uRole3.setDescription("Test Description 3");
        
        userRoles.save(uRole);
        userRoles.save(uRole2);
        userRoles.save(uRole3);
        
        UserRole shouldBeUserRole = userRoles.findById(uRole.getId()).orElse(null);
        UserRole shouldBeUserRole2 = userRoles.findById(uRole2.getId()).orElse(null);
        UserRole shouldBeUserRole3 = userRoles.findById(uRole3.getId()).orElse(null);
        Assertions.assertEquals(uRole, shouldBeUserRole);
        Assertions.assertEquals(uRole2, shouldBeUserRole2);
        Assertions.assertEquals(uRole3, shouldBeUserRole3);
    }
    @org.junit.jupiter.api.Test
    public void testSavedUpdatesObject() {
        UserRole uRole = new UserRole();
        uRole.setName("Test Name");
        uRole.setDescription("Test Description");

        userRoles.save(uRole);
        UserRole shouldBe1 = userRoles.findById(uRole.getId()).orElse(null);
        Assertions.assertEquals(uRole, shouldBe1);
        shouldBe1.setName("Test");
        userRoles.save(shouldBe1);
        UserRole shouldBe1Modified = userRoles.findById(uRole.getId()).orElse(null);
        long objectCount = userRoles.count();
        Assertions.assertEquals(shouldBe1Modified, shouldBe1);
        Assertions.assertEquals(1, objectCount);
    }
    @org.junit.jupiter.api.Test
    public void testDelete() {
        UserRole uRole = new UserRole();
        uRole.setName("Test Name");
        uRole.setDescription("Test Description");
        
        UserRole uRole2 = new UserRole();
        uRole2.setName("Test Name 2");
        uRole2.setDescription("Test Description 2");
        
        UserRole uRole3 = new UserRole();
        uRole3.setName("Test Name 3");
        uRole3.setDescription("Test Description 3");
        
        userRoles.save(uRole);
        userRoles.save(uRole2);
        userRoles.save(uRole3);
        
        long shouldBe3 = userRoles.count();
        userRoles.delete(uRole);
        userRoles.delete(uRole2);
        userRoles.delete(uRole3);
        long shouldBe0 = userRoles.count();
        Assertions.assertEquals(0, shouldBe0);
        Assertions.assertEquals(3, shouldBe3);
        
        UserRole uRoleNew = new UserRole();
        uRoleNew.setName("Test New Name");
        uRoleNew.setDescription("Test New Description");
        uRoleNew = userRoles.save(uRoleNew);
        userRoles.deleteById(uRoleNew.getId());
        long shouldBe0Again = userRoles.count();
        Assertions.assertEquals(0, shouldBe0Again);
    }
}
