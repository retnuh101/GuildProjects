/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.repos;

import com.srmsolutions.entities.JobRole;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class JobRoleRepositoryTest {
    
    @Autowired
    private JobRoleRepository jobRoles;


    @BeforeEach
    public  void setUp() {
        jobRoles.deleteAll();
    }
    @Test
    public void testSaveOneGetAll() {
        JobRole jRole = new JobRole();
        jRole.setName("Test Name");
        jRole.setDescription("Test Description");
        jRole.setDepartmentName("Test Department Name");
        jobRoles.save(jRole);
        List<JobRole> shouldHaveOne = jobRoles.findAll();
        Assertions.assertEquals(1, shouldHaveOne.size());
    }
    @Test
    public void testSaveManyGetAll() {
        JobRole jRole = new JobRole();
        jRole.setName("Test Name");
        jRole.setDescription("Test Description");
        jRole.setDepartmentName("Test Department Name");
        JobRole jRole2 = new JobRole();
        jRole2.setName("Test Name 2");
        jRole2.setDescription("Test Description 2");
        jRole2.setDepartmentName("Test Department Name 2");
        JobRole jRole3 = new JobRole();
        jRole3.setName("Test Name 3");
        jRole3.setDescription("Test Description 3");
        jRole3.setDepartmentName("Test Department Name 3");
        
        jobRoles.save(jRole);
        jobRoles.save(jRole2);
        jobRoles.save(jRole3);
        
        List<JobRole> shouldHaveThree = jobRoles.findAll();
        Assertions.assertEquals(3, shouldHaveThree.size());
    }
    //@org.junit.jupiter.api.Test
    @Test
    public void testFindByID() {
        JobRole jRole = new JobRole();
        jRole.setName("Test Name");
        jRole.setDescription("Test Description");
        jRole.setDepartmentName("Test Department Name");
        
        JobRole jRole2 = new JobRole();
        jRole2.setName("Test Name 2");
        jRole2.setDescription("Test Description 2");
        jRole2.setDepartmentName("Test Department Name 2");
        
        JobRole jRole3 = new JobRole();
        jRole3.setName("Test Name 3");
        jRole3.setDescription("Test Description 3");
        jRole3.setDepartmentName("Test Department Name 3");
        
        jobRoles.save(jRole);
        jobRoles.save(jRole2);
        jobRoles.save(jRole3);
        
        JobRole shouldBeJobRole = jobRoles.findById(jRole.getId()).orElse(null);
        JobRole shouldBeJobRole2 = jobRoles.findById(jRole2.getId()).orElse(null);
        JobRole shouldBeJobRole3 = jobRoles.findById(jRole3.getId()).orElse(null);
        Assertions.assertEquals(jRole, shouldBeJobRole);
        Assertions.assertEquals(jRole2, shouldBeJobRole2);
        Assertions.assertEquals(jRole3, shouldBeJobRole3);
    }
    @org.junit.jupiter.api.Test
    public void testSavedUpdatesObject() {
        JobRole jRole = new JobRole();
        jRole.setName("Test Name");
        jRole.setDescription("Test Description");
        jRole.setDepartmentName("Test Department Name");
        jobRoles.save(jRole);
        JobRole shouldBe1 = jobRoles.findById(jRole.getId()).orElse(null);
        Assertions.assertEquals(jRole, shouldBe1);
        shouldBe1.setName("Test");
        jobRoles.save(shouldBe1);
        JobRole shouldBe1Modified = jobRoles.findById(jRole.getId()).orElse(null);
        long objectCount = jobRoles.count();
        Assertions.assertEquals(shouldBe1Modified, shouldBe1);
        Assertions.assertEquals(1, objectCount);
    }
    @org.junit.jupiter.api.Test
    public void testDelete() {
        JobRole jRole = new JobRole();
        jRole.setName("Test Name");
        jRole.setDescription("Test Description");
        jRole.setDepartmentName("Test Department Name");
        
        JobRole jRole2 = new JobRole();
        jRole2.setName("Test Name 2");
        jRole2.setDescription("Test Description 2");
        jRole2.setDepartmentName("Test Department Name 2");
        
        JobRole jRole3 = new JobRole();
        jRole3.setName("Test Name 3");
        jRole3.setDescription("Test Description 3");
        jRole3.setDepartmentName("Test Department Name 3");
        
        jobRoles.save(jRole);
        jobRoles.save(jRole2);
        jobRoles.save(jRole3);
        
        long shouldBe3 = jobRoles.count();
        jobRoles.delete(jRole);
        jobRoles.delete(jRole2);
        jobRoles.delete(jRole3);
        long shouldBe0 = jobRoles.count();
        Assertions.assertEquals(0, shouldBe0);
        Assertions.assertEquals(3, shouldBe3);
        JobRole jRoleNew = new JobRole();
        jRoleNew.setName("Test New Name");
        jRoleNew.setDescription("Test New Description");
        jRoleNew.setDepartmentName("Test New Department Name");
        jRoleNew = jobRoles.save(jRoleNew);
        jobRoles.deleteById(jRoleNew.getId());
        long shouldBe0Again = jobRoles.count();
        Assertions.assertEquals(0, shouldBe0Again);
    }
    
}
