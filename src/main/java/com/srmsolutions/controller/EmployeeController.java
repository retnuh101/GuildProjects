package com.srmsolutions.controller;

import com.srmsolutions.entities.*;
import com.srmsolutions.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.*;

@Controller
public class EmployeeController {
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

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/loginGood")
    public String loginGood() {
        return "loginGood";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/")
    public String displayWeekAndEmployeeOfWeek(Model model, Principal principal) {
        LocalDate now = LocalDate.now();
        TemporalField fieldISO = WeekFields.of(Locale.FRANCE).dayOfWeek();
        LocalDate mondayOfCurrentWeek = now.with(fieldISO, 1);
        LocalDate sundayOfCurrentWeek = now.with(fieldISO, 7);
        List<Event> weekEvents = events.findByStartTimeBetween(mondayOfCurrentWeek, sundayOfCurrentWeek);
        model.addAttribute("eventNum", weekEvents.size());
        model.addAttribute("weekEvents", weekEvents);
        EmployeeOfWeek selectedEOW = weeks.findFirstByWeekStarting(now.with(fieldISO, 1));
        if (selectedEOW == null) {
            selectedEOW = weeks.findFirstByOrderByIdDesc();
        }
        model.addAttribute("employeeOfWeek", selectedEOW);
        if (null != principal) {
            Employee currentUser = employees.findByEmail(principal.getName());
            model.addAttribute("userGood", currentUser.isGood());
            return "index";
        } else {
            return "login";
        }
    }
    public static boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }
    @GetMapping("/employees")
    public String displayEmployees(Model model, Principal principal, Authentication authentication) {
//        principal.getName();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Collection<GrantedAuthority> authorities = userDetails.getAuthorities();
        List<Employee> allEmployees = employees.findAll();
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        for (Employee each : allEmployees) {
            Employee noManager = new Employee();
            noManager.setManagerName("no manager selected");
            Employee manager = employees.findById(each.getManagerId()).orElse(noManager);
            if (manager.equals(noManager)){
                each.setManagerName("no manager selected");
            } else {
                each.setManagerName(manager.getFirstName() + " " + manager.getLastName());
            }
        }
        List<Employee> justCurrentEmployee = new ArrayList<>();
        justCurrentEmployee.add(currentUser);
        employees.saveAll(allEmployees);
        if (hasRole("ROLE_ADMIN") || hasRole("ROLE_HR")) {
            model.addAttribute("employees", allEmployees);
        } else {
            model.addAttribute("employees", justCurrentEmployee);
        }

        return "employees";
    }
    @GetMapping("/employee/{id}")
    public String displayEmployeeDetail  (@PathVariable("id") String id, ModelMap model, Principal principal) {
        Employee selectedEmployee = employees.findById(Integer.parseInt(id)).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        Employee selectedManager = employees.findById(selectedEmployee.getManagerId()).orElse(null);
        selectedEmployee.setManagerName(selectedManager.getFirstName() + " " + selectedManager.getLastName());
        model.addAttribute("good", selectedEmployee.isGood());
        model.addAttribute("employee", selectedEmployee);
        model.addAttribute("hr", selectedEmployee.isHr());
        model.addAttribute("admin", selectedEmployee.isAdmin());
        model.addAttribute("events", events.findByAttendingListContaining(selectedEmployee));
        model.addAttribute("evenNum", events.findByAttendingListContaining(selectedEmployee).size());
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        if (!selectedEmployee.equals(currentUser) && !(hasRole("ROLE_ADMIN") || hasRole("ROLE_HR"))) {
         throw new IllegalArgumentException("Access to other employee records is DENIED.");
        }
        return "employee";
    }
    @GetMapping("/addEmployee")
    public String openAddLocation(Model model, Principal principal) {
        List<JobRole> jobRoleList = jobRoles.findAll();
        List<UserRole> userRoleList = userRoles.findAll();
        List<Employee> managerList = employees.findByJobRole(jobRoles.findById(5).orElse(null));
        model.addAttribute("managerList", managerList);
        model.addAttribute("userRoleList", userRoleList);
        model.addAttribute("jobRoleList", jobRoleList);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "addEmployee";
    }

    @PostMapping("/addEmployee")
    public String addEmployee(Employee employee, HttpServletRequest request, ModelMap model, RedirectAttributes redirAttrs) {
        List<Event> eventList = new ArrayList<>();
        employee.setUserRole(userRoles.getOne(Integer.parseInt(request.getParameter("userRoleId"))));
        employee.setJobRole(jobRoles.getOne(Integer.parseInt(request.getParameter("jobRoleId"))));
        LocalDate hireDate = LocalDate.parse(String.valueOf(request.getParameter("hireDate")));
        String termDateString = String.valueOf(request.getParameter("termDate"));
        if (termDateString.matches("/^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$/")) {
            employee.setTermDate(LocalDate.parse(request.getParameter("termDate")));
        }
        employee.setHireDate(hireDate);

        employee.setManagerId(Integer.parseInt(request.getParameter("manager")));
        Employee newEmployee = employees.save(employee);
        redirAttrs.addFlashAttribute("message", "Employee: <a href='/employee/" + employee.getId() + "'>" + employee.getFirstName() + " " + employee.getLastName() + "</a> has been successfully added.");
        String path = "redirect:/uploadPhoto/" + newEmployee.getId();
        return path;
    }

    @GetMapping("/editEmployee")
    public String editEmployee(HttpServletRequest request, Model model, Principal principal) {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employees.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
//        Employee employee = employees.findById(id).orElseThrow(new EmployeeNotFoundException("not found"));
        List<JobRole> jobRoleList = jobRoles.findAll();
        List<UserRole> userRoleList = userRoles.findAll();
        model.addAttribute("userRoleList", userRoleList);
        model.addAttribute("jobRoleList", jobRoleList);
        List<Employee> managerList = employees.findByJobRole(jobRoles.getOne(5));
        model.addAttribute("managerList", managerList);
        model.addAttribute("employee", employee);
        UploadForm uploadForm = new UploadForm();
        model.addAttribute("uploadForm", uploadForm);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "editEmployee";
    }
    @PostMapping("/editEmployee")
    public String editEmployee(HttpServletRequest request, RedirectAttributes redirAttrs) {
        int id = Integer.parseInt(request.getParameter("id"));
        Employee employee = employees.findById(id).orElse(null);
        String userRoleId = request.getParameter("userRoleId");
        String jobRoleId = request.getParameter("jobRoleId");
        employee.setFirstName(request.getParameter("firstName"));
        employee.setLastName(request.getParameter("lastName"));
        employee.setJobRole(jobRoles.getOne(Integer.parseInt(jobRoleId)));
        employee.setUserRole(userRoles.getOne(Integer.parseInt(userRoleId)));
        employee.setHireDate(LocalDate.parse(request.getParameter("hireDate")));
        String termDateString = request.getParameter("termDate");
        if (termDateString.matches("/^([\\+-]?\\d{4}(?!\\d{2}\\b))((-?)((0[1-9]|1[0-2])(\\3([12]\\d|0[1-9]|3[01]))?|W([0-4]\\d|5[0-2])(-?[1-7])?|(00[1-9]|0[1-9]\\d|[12]\\d{2}|3([0-5]\\d|6[1-6])))([T\\s]((([01]\\d|2[0-3])((:?)[0-5]\\d)?|24\\:?00)([\\.,]\\d+(?!:))?)?(\\17[0-5]\\d([\\.,]\\d+)?)?([zZ]|([\\+-])([01]\\d|2[0-3]):?([0-5]\\d)?)?)?)?$/")) {
            employee.setTermDate(LocalDate.parse(request.getParameter("termDate")));
        }
        employee.setSsn(request.getParameter("ssn"));
        employee.setHoursPto(Integer.parseInt(request.getParameter("hoursPto")));
        employee.setAddress(request.getParameter("address"));
        employee.setEmail(request.getParameter("email"));
        employee.setEmergencyContactName(request.getParameter("emergencyName"));
        employee.setEmergencyContactNumber(request.getParameter("emergencyNumber"));
        String booleanThing = request.getParameter("isGood");
        employee.setGood(Boolean.parseBoolean(request.getParameter("isGood")));
        employee.setHr(Boolean.parseBoolean(request.getParameter("isHr")));
        employee.setAdmin(Boolean.parseBoolean(request.getParameter("isAdmin")));
//        employee.setImageUrl(request.getParameter("imageUrl"));
        employee.setManagerId(Integer.parseInt(request.getParameter("manager")));
        employees.save(employee);
        redirAttrs.addFlashAttribute("message", "Employee: <a href='/employee/" + employee.getId() + "'>" + employee.getFirstName() + " " + employee.getLastName() + "</a> has been successfully edited.");
        return "redirect:/employees";
    }
    @GetMapping("/deleteEmployee")
    @Transactional
    public String deleteEmployee (Integer id, RedirectAttributes redirAttrs) {
        List<Event> allEvents = events.findAll();
        Employee toDelete = employees.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        for (Event event : allEvents) {
            List<Employee> attendingList = event.getAttendingList();
            attendingList.remove(toDelete);
            event.setAttendingList(attendingList);
            List<Employee> invitedList = event.getInviteList();
            invitedList.remove(toDelete);
            event.setInviteList(invitedList);
        }
        events.saveAll(allEvents);
        weeks.deleteAllByEmployee(toDelete);
        employees.delete(toDelete);
        redirAttrs.addFlashAttribute("message", "Employee: " + toDelete.getFirstName() + " " + toDelete.getLastName() + " has been successfully deleted.");
        return "redirect:/employees";
    }
}
