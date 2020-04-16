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
public class JobRoleController {
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
    
    @GetMapping("/jobRoles")
    public String displayJobRoles(Model m ,Principal principal){
        List<JobRole> allJobRoles = jobRoles.findAll();
        m.addAttribute("jobRoles", allJobRoles);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        return "jobRoles";
    }
    
    @GetMapping("/jobRole/{id}")
    public String displayJobRoleDetail(@PathVariable("id") String id, ModelMap m, Principal principal){
        JobRole selectedJobRole = jobRoles.findById(Integer.parseInt(id)).orElse(null);
        m.addAttribute("jobRole", selectedJobRole);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        return "jobRole";
    }
    
    @PostMapping("/addJobRole")
    public String addJobRole(JobRole jr, HttpServletRequest r, ModelMap m, RedirectAttributes ra){
        jobRoles.save(jr);
        ra.addFlashAttribute("message", "Job Role: " + jr.getName() + " has been successfully added.");
        return "redirect:/jobRoles";
    }
    
    @GetMapping("/editJobRole")
    public String editUserRole(HttpServletRequest r, Model m, Principal principal){
        int id = Integer.parseInt(r.getParameter("id"));
        JobRole jr = jobRoles.findById(id).orElse(null);
        m.addAttribute("jobRole", jr);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        return "editJobRole";
    }
    
    @PostMapping("/editJobRole")
    public String performJobRole(HttpServletRequest r, RedirectAttributes ra){
        int id = Integer.parseInt(r.getParameter("id"));
        JobRole jr = jobRoles.findById(id).orElse(null);
        jr.setName(r.getParameter("jobRoleName"));
        jr.setDescription(r.getParameter("description"));
        jr.setDepartmentName(r.getParameter("departmentName"));
        jobRoles.save(jr);
        ra.addFlashAttribute("message", "Job Role: " + jr.getName() + " has successfully been edited.");
        return "redirect:/jobRoles";
    }
    
    @GetMapping("deleteJobRole")
    public String deleteJobRole(Integer id, RedirectAttributes ra){
        jobRoles.deleteById(id);
        ra.addFlashAttribute("message", "Job Role has been successfully deleted.");
        return "redirect:/jobRoles";
    }
}
