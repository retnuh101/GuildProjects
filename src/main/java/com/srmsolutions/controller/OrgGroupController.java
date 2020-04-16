package com.srmsolutions.controller;

import com.srmsolutions.entities.*;
import com.srmsolutions.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class OrgGroupController {
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

    @GetMapping("/groups")
    public String displayGroups(Model model, Principal principal) {
        List<OrgGroup> allGroups = groups.findAll();
        model.addAttribute("allGroups", allGroups);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "groups";
    }

    @GetMapping("/addGroup")
    public String openAddGroup(Model model, Principal principal) {
        List<Employee> employeeList = employees.findAll();
        model.addAttribute("employeeList", employeeList);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "addGroup";
    }
//
    @PostMapping("/addGroup")
    public String addGroup(OrgGroup orgGroup, HttpServletRequest request, ModelMap model, RedirectAttributes redirAttrs) {
        populateOrgFromRequest(request, orgGroup);
        redirAttrs.addFlashAttribute("message", "Group: " + orgGroup.getName() + " has been successfully added.");
        return "redirect:/groups";
    }

    @GetMapping("/editGroup")
    public String editGroup(HttpServletRequest request, Model model, Principal principal) {
        int id = Integer.parseInt(request.getParameter("id"));
        OrgGroup group = groups.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
//        Employee employee = employees.findById(id).orElseThrow(new EmployeeNotFoundException("not found"));
        List<Employee> otherEmployeeList = employees.findAll();
       for (Employee employee : group.getEmployeeList()) {
           otherEmployeeList.remove(employee);
       }
       model.addAttribute("group", group);
        model.addAttribute("otherEmployeeList", otherEmployeeList);
        model.addAttribute("currentEmployeeList", group.getEmployeeList());
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "editGroup";
    }
    @PostMapping("/editGroup")
    public String editGroup(HttpServletRequest request, RedirectAttributes redirAttrs) {
        int id = Integer.parseInt(request.getParameter("id"));
        OrgGroup orgGroup = groups.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        populateOrgFromRequest(request, orgGroup);
        redirAttrs.addFlashAttribute("message", "Group: " + orgGroup.getName() + " has been successfully edited.");
        return "redirect:/groups";
    }

    private void populateOrgFromRequest(HttpServletRequest request, OrgGroup orgGroup) {
        orgGroup.setName(request.getParameter("name"));
        String[] employeeIds = request.getParameterValues("employeesInGroup");
        List<Employee> employeeList = new ArrayList<>();
        if (employeeIds.length != 0 && employeeIds !=null) {
            for (String each : employeeIds) {
                employeeList.add(employees.getOne(Integer.parseInt(each)));
            }
        }
        orgGroup.setEmployeeList(employeeList);
        groups.save(orgGroup);
    }
    @GetMapping("/deleteGroup")
    @Transactional
    public String deleteGroup (Integer id, RedirectAttributes redirAttrs) {
        OrgGroup toDelete = groups.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid group Id:" + id));
        groups.delete(toDelete);
        redirAttrs.addFlashAttribute("message", "Group: " + toDelete.getName() + " has been successfully deleted.");
        return "redirect:/groups";
    }
}
