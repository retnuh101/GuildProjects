/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.repos;

import com.srmsolutions.entities.Category;
import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.Event;
import com.srmsolutions.entities.UserRole;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.util.Lists.newArrayList;
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
public class EventRepositoryTest {
    
    @Autowired
    private EventRepository events;
    @Autowired
    private CategoryRepository categories;
    @Autowired
    private EmployeeRepository employees;
    
    @BeforeEach
    public  void setUp() {
        events.deleteAll();
        categories.deleteAll();
        employees.deleteAll();
    }
    @org.junit.jupiter.api.Test
    public void testSaveOneGetAll() {
        Event event = new Event();
        event.setName("Test Name");
        List<Category> categoryList = new ArrayList<>();
        event.setCategoryList(categoryList);
        List<Employee> inviteList = new ArrayList<>();
        event.setInviteList(inviteList);
        List<Employee> attendingList = new ArrayList<>();
        event.setAttendingList(attendingList);
        event.setStartTime(LocalDate.MIN);
        event.setEndTime(LocalDate.MAX);
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setRequired(true);
        event.setLatitude(0);
        event.setLongitude(0);

        events.save(event);
        List<Event> shouldHaveOne = events.findAll();
        Assertions.assertEquals(1, shouldHaveOne.size());
    }
    
    @org.junit.jupiter.api.Test
    public void testSaveManyGetAll() {
        Event event = new Event();
        event.setName("Test Name");
        List<Category> categoryList = new ArrayList<>();
        event.setCategoryList(categoryList);
        List<Employee> inviteList = new ArrayList<>();
        event.setInviteList(inviteList);
        List<Employee> attendingList = new ArrayList<>();
        event.setAttendingList(attendingList);
        event.setStartTime(LocalDate.MIN);
        event.setEndTime(LocalDate.MAX);
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setRequired(true);
        event.setLatitude(0);
        event.setLongitude(0);
       
        Event event2 = new Event();
        event2.setName("Test Name 2");
        List<Category> categoryList2 = new ArrayList<>();
        event2.setCategoryList(categoryList2);
        List<Employee> inviteList2 = new ArrayList<>();
        event2.setInviteList(inviteList2);
        List<Employee> attendingList2 = new ArrayList<>();
        event2.setAttendingList(attendingList2);
        event2.setStartTime(LocalDate.MIN);
        event2.setEndTime(LocalDate.MAX);
        event2.setDescription("Test Description 2");
        event2.setLocation("Test Location 2");
        event2.setRequired(true);
        event2.setLatitude(2);
        event2.setLongitude(2);
        
        Event event3 = new Event();
        event3.setName("Test Name 3");
        List<Category> categoryList3 = new ArrayList<>();
        event3.setCategoryList(categoryList3);
        List<Employee> inviteList3 = new ArrayList<>();
        event3.setInviteList(inviteList3);
        List<Employee> attendingList3 = new ArrayList<>();
        event3.setAttendingList(attendingList3);
        event3.setStartTime(LocalDate.MIN);
        event3.setEndTime(LocalDate.MAX);
        event3.setDescription("Test Description 3");
        event3.setLocation("Test Location 3");
        event3.setRequired(true);
        event3.setLatitude(3);
        event3.setLongitude(3);

        events.save(event);
        events.save(event2);
        events.save(event3);
        
        List<Event> shouldHaveThree = events.findAll();
        Assertions.assertEquals(3, shouldHaveThree.size());
    }
    //@org.junit.jupiter.api.Test
    @org.junit.jupiter.api.Test
    public void testFindByID() {
        Event event = new Event();
        event.setName("Test Name");
        List<Category> categoryList = new ArrayList<>();
        event.setCategoryList(categoryList);
        List<Employee> inviteList = new ArrayList<>();
        event.setInviteList(inviteList);
        List<Employee> attendingList = new ArrayList<>();
        event.setAttendingList(attendingList);
        event.setStartTime(LocalDate.MIN);
        event.setEndTime(LocalDate.MAX);
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setRequired(true);
        event.setLatitude(0);
        event.setLongitude(0);
       
        Event event2 = new Event();
        event2.setName("Test Name 2");
        List<Category> categoryList2 = new ArrayList<>();
        event2.setCategoryList(categoryList2);
        List<Employee> inviteList2 = new ArrayList<>();
        event2.setInviteList(inviteList2);
        List<Employee> attendingList2 = new ArrayList<>();
        event2.setAttendingList(attendingList2);
        event2.setStartTime(LocalDate.MIN);
        event2.setEndTime(LocalDate.MAX);
        event2.setDescription("Test Description 2");
        event2.setLocation("Test Location 2");
        event2.setRequired(true);
        event2.setLatitude(2);
        event2.setLongitude(2);
        
        Event event3 = new Event();
        event3.setName("Test Name 3");
        List<Category> categoryList3 = new ArrayList<>();
        event3.setCategoryList(categoryList3);
        List<Employee> inviteList3 = new ArrayList<>();
        event3.setInviteList(inviteList3);
        List<Employee> attendingList3 = new ArrayList<>();
        event3.setAttendingList(attendingList3);
        event3.setStartTime(LocalDate.MIN);
        event3.setEndTime(LocalDate.MAX);
        event3.setDescription("Test Description 3");
        event3.setLocation("Test Location 3");
        event3.setRequired(true);
        event3.setLatitude(3);
        event3.setLongitude(3);

        events.save(event);
        events.save(event2);
        events.save(event3);
        
        Event shouldBeEvent= events.findById(event.getId()).orElse(null);
        Event shouldBeEvent2 = events.findById(event2.getId()).orElse(null);
        Event shouldBeEvent3 = events.findById(event3.getId()).orElse(null);
        Assertions.assertEquals(event, shouldBeEvent);
        Assertions.assertEquals(event2, shouldBeEvent2);
        Assertions.assertEquals(event3, shouldBeEvent3);
    }
    @org.junit.jupiter.api.Test
    public void testSavedUpdatesObject() {
        Event event = new Event();
        event.setName("Test Name");
        List<Category> categoryList = new ArrayList<>();
        event.setCategoryList(categoryList);
        List<Employee> inviteList = new ArrayList<>();
        event.setInviteList(inviteList);
        List<Employee> attendingList = new ArrayList<>();
        event.setAttendingList(attendingList);
        event.setStartTime(LocalDate.MIN);
        event.setEndTime(LocalDate.MAX);
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setRequired(true);
        event.setLatitude(0);
        event.setLongitude(0);

        events.save(event);
        Event shouldBe1 = events.findById(event.getId()).orElse(null);
        Assertions.assertEquals(event, shouldBe1);
        shouldBe1.setName("Test");
        events.save(shouldBe1);
        Event shouldBe1Modified = events.findById(event.getId()).orElse(null);
        long objectCount = events.count();
        Assertions.assertEquals(shouldBe1Modified, shouldBe1);
        Assertions.assertEquals(1, objectCount);
    }
    @org.junit.jupiter.api.Test
    public void testDelete() {
        Event event = new Event();
        event.setName("Test Name");
        List<Category> categoryList = new ArrayList<>();
        event.setCategoryList(categoryList);
        List<Employee> inviteList = new ArrayList<>();
        event.setInviteList(inviteList);
        List<Employee> attendingList = new ArrayList<>();
        event.setAttendingList(attendingList);
        event.setStartTime(LocalDate.MIN);
        event.setEndTime(LocalDate.MAX);
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        event.setRequired(true);
        event.setLatitude(0);
        event.setLongitude(0);
       
        Event event2 = new Event();
        event2.setName("Test Name 2");
        List<Category> categoryList2 = new ArrayList<>();
        event2.setCategoryList(categoryList2);
        List<Employee> inviteList2 = new ArrayList<>();
        event2.setInviteList(inviteList2);
        List<Employee> attendingList2 = new ArrayList<>();
        event2.setAttendingList(attendingList2);
        event2.setStartTime(LocalDate.MIN);
        event2.setEndTime(LocalDate.MAX);
        event2.setDescription("Test Description 2");
        event2.setLocation("Test Location 2");
        event2.setRequired(true);
        event2.setLatitude(2);
        event2.setLongitude(2);
        
        Event event3 = new Event();
        event3.setName("Test Name 3");
        List<Category> categoryList3 = new ArrayList<>();
        event3.setCategoryList(categoryList3);
        List<Employee> inviteList3 = new ArrayList<>();
        event3.setInviteList(inviteList3);
        List<Employee> attendingList3 = new ArrayList<>();
        event3.setAttendingList(attendingList3);
        event3.setStartTime(LocalDate.MIN);
        event3.setEndTime(LocalDate.MAX);
        event3.setDescription("Test Description 3");
        event3.setLocation("Test Location 3");
        event3.setRequired(true);
        event3.setLatitude(3);
        event3.setLongitude(3);

        events.save(event);
        events.save(event2);
        events.save(event3);
        
        long shouldBe3 = events.count();
        events.delete(event);
        events.delete(event2);
        events.delete(event3);
        long shouldBe0 = events.count();
        Assertions.assertEquals(0, shouldBe0);
        Assertions.assertEquals(3, shouldBe3);
        
        Event eventNew = new Event();
        eventNew.setName("Test New Name");
        eventNew.setDescription("Test New Description");
        eventNew = events.save(eventNew);
        events.deleteById(eventNew.getId());
        long shouldBe0Again = events.count();
        Assertions.assertEquals(0, shouldBe0Again);
    }
}
