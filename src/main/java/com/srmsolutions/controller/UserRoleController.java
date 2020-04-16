/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.srmsolutions.controller;

import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.UserRole;
import com.srmsolutions.repos.CategoryRepository;
import com.srmsolutions.repos.EmployeeOfWeekRepository;
import com.srmsolutions.repos.EmployeeRepository;
import com.srmsolutions.repos.EventRepository;
import com.srmsolutions.repos.JobRoleRepository;
import com.srmsolutions.repos.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.*;
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
public class UserRoleController {
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

    @GetMapping("/userRoles")
    public String displayUserRoles(Model model, Principal principal){
        List<UserRole> allUserRoles = userRoles.findAll();
        model.addAttribute("userRoles", allUserRoles);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "userRoles";
    }
    
    @GetMapping("/userRole/{id}")
    public String displayUserRoleDetail(@PathVariable("id") String id, ModelMap model, Principal principal){
        UserRole selectedUserRole = userRoles.findById(Integer.parseInt(id)).orElse(null);
        model.addAttribute("userRole", selectedUserRole);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "userRole";
    }
    
    @PostMapping("/addUserRole")
    public String addUserRole(UserRole userRole, HttpServletRequest request, ModelMap model, RedirectAttributes redirAttrs){
        userRoles.save(userRole);
        redirAttrs.addFlashAttribute("message", "User Role: " + userRole.getName() + " has been successfully added.");
        return "redirect:/userRoles";
    }
    
    @GetMapping("/editUserRole")
    public String editUserRole(HttpServletRequest r, Model m, Principal principal){
        int id = Integer.parseInt(r.getParameter("id"));
        UserRole ur = userRoles.findById(id).orElse(null);
        m.addAttribute("userRole", ur);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        return "editUserRole";
    }
    
    @PostMapping("/editUserRole")
    public String performEditUserRole(HttpServletRequest r, RedirectAttributes reAttrs){
        int id = Integer.parseInt(r.getParameter("id"));
        UserRole ur = userRoles.findById(id).orElse(null);
        ur.setName(r.getParameter("userRoleName"));
        ur.setDescription(r.getParameter("description"));
        userRoles.save(ur);
        reAttrs.addFlashAttribute("message", "User Role " + ur.getName() + " has been successfully edited.");
        return "redirect:/userRoles";
    }
    
    @GetMapping("/deleteUserRole")
    public String deleteUserRole(Integer id, RedirectAttributes ra){
        userRoles.deleteById(id);
        ra.addFlashAttribute("message", "User Role has been successfully deleted.");
        return "redirect:/userRoles";
    }
    
}
