/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.controller;

import com.srmsolutions.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.*;
import com.srmsolutions.entities.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 *
 * @author hschuler2992
 */
@Controller
public class EventController {
    @Autowired
    CategoryRepository categories;
    @Autowired
    EmployeeRepository employees;
    @Autowired
    UserRoleRepository userRoles;
    @Autowired
    JobRoleRepository jobRoles;
    @Autowired
    EmployeeOfWeekRepository weeks;
    @Autowired
    EventRepository events;
    @Autowired
    OrgGroupRepository groups;
    public static boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
    @GetMapping("/events")
    public String displayEvents(Model model, Principal principal){
        List<Category> allCategories = categories.findAll();
        List<Employee> allEmployees = employees.findAll();
        List<Event> allEvents = events.findAll();
        model.addAttribute("categories", allCategories);
        model.addAttribute("employees", allEmployees);
        model.addAttribute("events", allEvents);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        List<Event> publicAndOwnEvents = new ArrayList<>();
        publicAndOwnEvents.addAll(events.findByInviteListContaining(currentUser));
        publicAndOwnEvents.addAll(events.findByIsPublicTrue());
        employees.saveAll(allEmployees);
        if (hasRole("ROLE_ADMIN") || hasRole("ROLE_HR") || hasRole("ROLE_EO")) {
            model.addAttribute("events", allEvents);
        } else {
            model.addAttribute("events", publicAndOwnEvents);
        }
        return "events";
    }
    
    @GetMapping("/event/{id}")
    public String displayEventDetail(@PathVariable("id") String id, ModelMap model, Principal principal){
        Event event = events.findById(Integer.parseInt(id)).orElseThrow(() -> new IllegalArgumentException("Invalid event ID:" + id));
        model.addAttribute("event", event);
        model.addAttribute("inviteNum", event.getInviteList().size());
        List<Employee> employeeInvitedList = event.getInviteList();
        List<Employee> employeeAttendingList = event.getAttendingList();
        List<Employee> invitedNotAttending = new ArrayList<>();
        for (Employee employee : employeeInvitedList) {
            if (!employeeAttendingList.contains(employee)) {
                invitedNotAttending.add(employee);
            }
        }
        List<Category> categoryList = categories.findAll();
        model.addAttribute("invitedNotAttending", invitedNotAttending);
        model.addAttribute("employeeInvitedList", employeeInvitedList);
        model.addAttribute("employeeAttendingList", employeeAttendingList);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "fragments/stylingFragment :: eventModalContents";
    }
    
    @GetMapping("/addEvent")
    public String openAddLocation(Model model, Principal principal) {
        List<Category> allCategories = categories.findAll();
        List<Employee> allEmployees = employees.findAll();
        List<Employee> allEOs = employees.findByUserRole(userRoles.findById(2).orElse(null));
        model.addAttribute("categories", allCategories);
        model.addAttribute("employees", allEmployees);
        model.addAttribute("groupList", groups.findAll());
        model.addAttribute("organizers", allEOs);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "addEvent";
    }
    
    @PostMapping("/addEvent")
    public String addEvent(Event event, HttpServletRequest r, ModelMap model, RedirectAttributes ra){
        String[] employeeInvitedIds = r.getParameterValues("addInvitedEmployeeId");
        String[] categoryIds = r.getParameterValues("addCategoryId");
        String location = r.getParameter("placeName");
        event.setLocation(location);
        event.setOrganizer(employees.findById(Integer.parseInt(r.getParameter("organizer"))).orElse(null));
        event.setOrganizingGroup(groups.findById(Integer.parseInt(r.getParameter("group"))).orElse(null));
        
        List<Category> categoryList = new ArrayList<>();
        for(String categoryId : categoryIds){
            categoryList.add(categories.findById(Integer.parseInt(categoryId)).orElse(null));
        }
        
        List<Employee> employeeInvitedList = new ArrayList<>();
        for(String employeeInvitedId : employeeInvitedIds){
            employeeInvitedList.add(employees.findById(Integer.parseInt(employeeInvitedId)).orElse(null));
        }
        
        event.setCategoryList(categoryList);
        event.setInviteList(employeeInvitedList);
        
        event.setName(r.getParameter("eventName"));
        
        String startTimeAsString = r.getParameter("startTime");
        LocalDate startTime = LocalDate.parse(startTimeAsString);
        event.setStartTime(startTime);
        
        String endTimeAsString = r.getParameter("endTime");
        if (!endTimeAsString.equals("")) {
            LocalDate endTime = LocalDate.parse(endTimeAsString);
            event.setEndTime(endTime);
        }
        
        event.setDescription(r.getParameter("description"));
        
        if(r.getParameter("isRequired") != null){
            event.setRequired(true);
        } else {
            event.setRequired(false);
        }
        
        String latAsString = r.getParameter("latitude");
        double latitude = Double.parseDouble(latAsString);
        event.setLatitude(latitude);
        
        String longAsString = r.getParameter("longitude");
        double longitude = Double.parseDouble(longAsString);
        event.setLongitude(longitude);
        
        events.save(event);
        ra.addFlashAttribute("message", "Event: <a onclick='javascript:openEventModal(" + event.getId() + ");'>" + event.getName() + "</a> has been successfully added.");
        return "redirect:/events";
    }
    
    @GetMapping("/editEvent")
    public String editEvent(HttpServletRequest r, Model m, Principal principal){
        
        int id = Integer.parseInt(r.getParameter("id"));
        Event event = events.findById(id).orElse(null);
        
        List<Employee> employeeInvitedList = event.getInviteList();
        List<Employee> employeeAttendingList = event.getAttendingList();
        List<Employee> invitedNotAttending = new ArrayList<>();
        for (Employee employee : employeeInvitedList) {
            if (!employeeAttendingList.contains(employee)) {
                invitedNotAttending.add(employee);
            }
        }
        List<Category> eventCategories = event.getCategoryList();
        List<Category> allCategories = categories.findAll();
        List<Category> otherCategories = new ArrayList<>();
        for (Category cat : allCategories) {
            if (!eventCategories.contains(cat)) {
                otherCategories.add(cat);
            }
        }
        m.addAttribute("invitedNotAttending", invitedNotAttending);
        m.addAttribute("employeeInvitedList", employeeInvitedList);
        m.addAttribute("employeeAttendingList", employeeAttendingList);
        m.addAttribute("otherCategoryList", otherCategories);
        
        m.addAttribute("event", event);
        List<Employee> allEOs = employees.findByUserRole(userRoles.findById(2).orElse(null));
        List<Employee> allEmployees = employees.findAll();

        m.addAttribute("employees", allEmployees);
        m.addAttribute("groupList", groups.findAll());
        m.addAttribute("organizers", allEOs);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        return "editEvent";
    }

    @PostMapping("/editEvent")
    public String performEditEvent(HttpServletRequest r, RedirectAttributes ra){
        String[] employeeAttendingIds = r.getParameterValues("editAttendingEmployeeId");
        String[] categoryIds = r.getParameterValues("editCategoryId");

        int id = Integer.parseInt(r.getParameter("id"));
        Event event = events.findById(id).orElse(null);
        event.setName(r.getParameter("eventName"));
        event.setDescription("description");

        event.setOrganizer(employees.findById(Integer.parseInt(r.getParameter("organizer"))).orElse(null));
        event.setOrganizingGroup(groups.findById(Integer.parseInt(r.getParameter("group"))).orElse(null));
        if(r.getParameter("isRequired") != null){
            event.setRequired(true);
        } else {
            event.setRequired(false);
        }
        String location = r.getParameter("placeName");
        event.setLocation(location);
        String latAsString = r.getParameter("latitude");
        double latitude = Double.parseDouble(latAsString);
        event.setLatitude(latitude);
        
        String longAsString = r.getParameter("longitude");
        double longitude = Double.parseDouble(longAsString);
        event.setLongitude(longitude);
        
        List<Employee> employeeAttendingList = new ArrayList<>();
        for(String employeeAttendingId : employeeAttendingIds){
            employeeAttendingList.add(employees.findById(Integer.parseInt(employeeAttendingId)).orElse(null));
        }
        
        List<Category> categoryList = new ArrayList<>();
        for(String categoryId : categoryIds){
            categoryList.add(categories.findById(Integer.parseInt(categoryId)).orElse(null));
        }
        event.setCategoryList(categoryList);
        event.setAttendingList(employeeAttendingList);

        
        String startTimeAsString = r.getParameter("startTime");
        LocalDate startTime = LocalDate.parse(startTimeAsString);
        event.setStartTime(startTime);
        
        String endTimeAsString = r.getParameter("endTime");
        LocalDate endTime = LocalDate.parse(endTimeAsString);
        event.setEndTime(endTime);
        
        events.save(event);
        ra.addFlashAttribute("message", "Event: <a onclick='javascript:openEventModal(" + event.getId() + ");'>" + event.getName() + "</a> has been successfully edited.");

        return "redirect:/events";
    }

    @GetMapping("/deleteEvent")
    public String deleteEvent(Integer id, RedirectAttributes ra){
        Event toDelete = events.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        events.deleteById(id);
        ra.addFlashAttribute("message", "Event: " + toDelete.getName() + " has been successfully deleted.");
        return "redirect:/events";
    }
}
