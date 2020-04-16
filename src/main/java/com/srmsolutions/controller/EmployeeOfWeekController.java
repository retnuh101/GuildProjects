/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.controller;

import com.srmsolutions.repos.CategoryRepository;
import com.srmsolutions.repos.EmployeeOfWeekRepository;
import com.srmsolutions.repos.EmployeeRepository;
import com.srmsolutions.repos.EventRepository;
import com.srmsolutions.repos.JobRoleRepository;
import com.srmsolutions.repos.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.*;
import com.srmsolutions.entities.*;
import java.time.LocalDate;
import java.time.Month;
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
public class EmployeeOfWeekController {
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

    public static boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    @GetMapping("/employeeOfWeeks")
    public String displayEmployeeOfWeek(Model model, Principal principal){
        List<Employee> allEmployees = employees.findAll();
        List<EmployeeOfWeek> allEmployeeOfWeek = weeks.findAll();
        model.addAttribute("employees", allEmployees);
        model.addAttribute("employeeOfWeeks", allEmployeeOfWeek);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "employeeOfWeeks";
    }
    
    @GetMapping("/employeeOfWeek/{id}")
    public String displayEmployeeOfWeekDetail(@PathVariable("id") String id, ModelMap model, Principal principal){
        EmployeeOfWeek selectedEOW = weeks.findById(Integer.parseInt(id)).orElse(null);
        model.addAttribute("employeeOfWeek", selectedEOW);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "employeeOfWeek";
    }
    
    @GetMapping("/addEmployeeOfWeek")
    public String addEmployeeOfWeek(Model m, Principal principal){
        List<Employee> employeeList = employees.findAll();
        List<EmployeeOfWeek> eowList = weeks.findAll();
        m.addAttribute("employees", employeeList);
        m.addAttribute("employeeOfWeeks", eowList);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        return "addEmployeeOfWeek";
    }
    
    @PostMapping("/addEmployeeOfWeek")
    public String performAddEmployeeOfWeek(EmployeeOfWeek eow, HttpServletRequest r, ModelMap m, RedirectAttributes ra){
        eow.setDescription(r.getParameter("description"));
        String weekStart = r.getParameter("weekStarting");
        LocalDate weekStarting = LocalDate.parse(weekStart);
        eow.setWeekStarting(weekStarting);
        eow.setEmployee(employees.findById(Integer.parseInt(r.getParameter("employeeId"))).orElse(null));
        weeks.save(eow);
        ra.addFlashAttribute("message", "Employee of the Week: <a href='/employee/" + eow.getEmployee().getId() + "'>" + eow.getEmployee().getFirstName() + " " + eow.getEmployee().getLastName() +  "</a> has been successfully added.");
        return "redirect:/employeeOfWeeks";
    }
    
    @GetMapping("/editEmployeeOfWeek")
    public String editEmployeeOfWeek(HttpServletRequest r, Model m, Principal principal){
        int id = Integer.parseInt(r.getParameter("id"));
        EmployeeOfWeek eow = weeks.findById(id).orElse(null);
        List<Employee> employeeList = employees.findAll();
        m.addAttribute("employeeOfWeek", eow);
        m.addAttribute("employees", employeeList);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        return "editEmployeeOfWeek";
    }
    
    @PostMapping("/editEmployeeOfWeek")
    public String performEditEmployeeOfWeek(HttpServletRequest r, RedirectAttributes ra){
        int id = Integer.parseInt(r.getParameter("id"));
        EmployeeOfWeek eow = weeks.findById(id).orElse(null);
        int employeeId = Integer.parseInt(r.getParameter("employeeId"));
        Employee e = employees.findById(employeeId).orElse(null);
        eow.setEmployee(e);
        eow.setDescription(r.getParameter("description"));
        String weekStart = r.getParameter("weekStarting");
        LocalDate weekStarting = LocalDate.parse(weekStart);
        eow.setWeekStarting(weekStarting);
        weeks.save(eow);
        ra.addFlashAttribute("message", "Employee of the Week: <a href='/employee/" + eow.getEmployee().getId() + "'>" + eow.getEmployee().getFirstName() + " " + eow.getEmployee().getLastName() +  "</a> has been successfully edited.");
        return "redirect:/employeeOfWeeks";
    }
    
    @GetMapping("/deleteEmployeeOfWeek")
    public String deleteEmployeeOfWeek(Integer id, RedirectAttributes ra){
        EmployeeOfWeek toDelete = weeks.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid event Id:" + id));
        weeks.deleteById(id);
        ra.addFlashAttribute("message", "Employee of the Week: " + toDelete.getEmployee().getFirstName() + " " + toDelete.getEmployee().getLastName() + " has been successfully deleted.");
        return "redirect:/employeeOfWeeks";
    }
    
}
