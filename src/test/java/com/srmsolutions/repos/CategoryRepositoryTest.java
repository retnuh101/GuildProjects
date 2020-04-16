/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.repos;

import com.srmsolutions.entities.Category;
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
public class CategoryRepositoryTest {
    
    @Autowired
    private CategoryRepository categories;
    
    @BeforeEach
    public  void setUp() {
        categories.deleteAll();
    }
    @org.junit.jupiter.api.Test
    public void testSaveOneGetAll() {
        Category category = new Category();
        category.setName("Test Name");
        category.setDescription("Test Description");
        categories.save(category);
        List<Category> shouldHaveOne = categories.findAll();
        Assertions.assertEquals(1, shouldHaveOne.size());
    }
    @org.junit.jupiter.api.Test
    public void testSaveManyGetAll() {
        Category category = new Category();
        category.setName("Test Name");
        category.setDescription("Test Description");
       
        Category category2 = new Category();
        category2.setName("Test Name 2");
        category2.setDescription("Test Description 2");
        
        Category category3 = new Category();
        category3.setName("Test Name 3");
        category3.setDescription("Test Description 3");
        
        categories.save(category);
        categories.save(category2);
        categories.save(category3);
        
        List<Category> shouldHaveThree = categories.findAll();
        Assertions.assertEquals(3, shouldHaveThree.size());
    }
    //@org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Test
    public void testFindByID() {
        Category category = new Category();
        category.setName("Test Name");
        category.setDescription("Test Description");
        
        Category category2 = new Category();
        category2.setName("Test Name 2");
        category2.setDescription("Test Description 2");
        
        Category category3 = new Category();
        category3.setName("Test Name 3");
        category3.setDescription("Test Description 3");
        
        categories.save(category);
        categories.save(category2);
        categories.save(category3);
        
        Category shouldBeCategory = categories.findById(category.getId()).orElse(null);
        Category shouldBeCategory2 = categories.findById(category2.getId()).orElse(null);
        Category shouldBeCategory3 = categories.findById(category3.getId()).orElse(null);
        Assertions.assertEquals(category, shouldBeCategory);
        Assertions.assertEquals(category2, shouldBeCategory2);
        Assertions.assertEquals(category3, shouldBeCategory3);
    }
    @org.junit.jupiter.api.Test
    public void testSavedUpdatesObject() {
        Category category = new Category();
        category.setName("Test Name");
        category.setDescription("Test Description");

        categories.save(category);
        Category shouldBe1 = categories.findById(category.getId()).orElse(null);
        Assertions.assertEquals(category, shouldBe1);
        shouldBe1.setName("Test");
        categories.save(shouldBe1);
        Category shouldBe1Modified = categories.findById(category.getId()).orElse(null);
        long objectCount = categories.count();
        Assertions.assertEquals(shouldBe1Modified, shouldBe1);
        Assertions.assertEquals(1, objectCount);
    }
    @org.junit.jupiter.api.Test
    public void testDelete() {
        Category category = new Category();
        category.setName("Test Name");
        category.setDescription("Test Description");
        
        Category category2 = new Category();
        category2.setName("Test Name 2");
        category2.setDescription("Test Description 2");
        
        Category category3 = new Category();
        category3.setName("Test Name 3");
        category3.setDescription("Test Description 3");
        
        categories.save(category);
        categories.save(category2);
        categories.save(category3);
        
        long shouldBe3 = categories.count();
        categories.delete(category);
        categories.delete(category2);
        categories.delete(category3);
        long shouldBe0 = categories.count();
        Assertions.assertEquals(0, shouldBe0);
        Assertions.assertEquals(3, shouldBe3);
        
        Category categoryNew = new Category();
        categoryNew.setName("Test New Name");
        categoryNew.setDescription("Test New Description");
        categoryNew = categories.save(categoryNew);
        categories.deleteById(categoryNew.getId());
        long shouldBe0Again = categories.count();
        Assertions.assertEquals(0, shouldBe0Again);
    }
    
}
