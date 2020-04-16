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
public class CategoryController {
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
    
    @GetMapping("/categories")
    public String displayCategories(Model model , Principal principal){
        List<Category> allCategories = categories.findAll();
        model.addAttribute("categories", allCategories);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "categories";
    }
    
    @GetMapping("/category/{id}")
    public String displayCategoryDetail(@PathVariable("id") String id, ModelMap model, Principal principal){
        Category selectedCategory = categories.findById(Integer.parseInt(id)).orElse(null);
        model.addAttribute("category", selectedCategory);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "category";
    }
    
    @PostMapping("/addCategory")
    public String addCategory(Category c , HttpServletRequest r, ModelMap m, RedirectAttributes ra){
        categories.save(c);
        ra.addFlashAttribute("message", "Category: " + c.getName() + " has been successfully added.");
        return "redirect:/categories";
    }
    
    @GetMapping("/editCategory")
    public String editCategory(HttpServletRequest r, Model m, Principal principal){
        int id = Integer.parseInt(r.getParameter("id"));
        Category c = categories.findById(id).orElse(null);
        Employee currentUser =  employees.findByEmail( principal.getName());
        m.addAttribute("userGood", currentUser.isGood());
        m.addAttribute("category", c);
        return "editCategory";
    }
    
    @PostMapping("/editCategory")
    public String performEditCategory(HttpServletRequest r, RedirectAttributes ra){
        int id = Integer.parseInt(r.getParameter("id"));
        Category c = categories.findById(id).orElse(null);
        c.setName(r.getParameter("categoryName"));
        c.setDescription(r.getParameter("description"));
        categories.save(c);

        ra.addFlashAttribute("message", "Category: " + c.getName() + " has been successfully edited.");
        return "redirect:/categories";
    }
    
    @GetMapping("/deleteCategory")
    public String deleteCategory(Integer id, RedirectAttributes ra){
        categories.deleteById(id);
        ra.addFlashAttribute("message", "Category has been successfully deleted.");
        return "redirect:/categories";
    }
    
}
