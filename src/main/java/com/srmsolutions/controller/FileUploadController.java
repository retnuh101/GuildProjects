package com.srmsolutions.controller;

import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.UploadForm;
import com.srmsolutions.repos.EmployeeRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FileUploadController {

    @Autowired
    EmployeeRepository employees;

    public static boolean hasRole (String roleName)
    {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

    @GetMapping("/uploadPhoto/{id}")
    public String uploadPhotoPage(@PathVariable("id") String id, Model model, Principal principal) {
        model.addAttribute("id", id);
        UploadForm uploadForm = new UploadForm();
        model.addAttribute("uploadForm", uploadForm);
        Employee currentUser =  employees.findByEmail( principal.getName());
        model.addAttribute("userGood", currentUser.isGood());
        return "uploadPhoto";
    }

    @PostMapping("/uploadPhoto")
    public String uploadOneFileHandlerPOST( HttpServletRequest request,
                                                   Model model,
                                           @ModelAttribute("uploadForm") UploadForm uploadForm, RedirectAttributes redirAttrs) {

        return this.doUpload(request, model, uploadForm, redirAttrs);

    }

    @Autowired
    ServletContext context;



    private String doUpload(HttpServletRequest request, Model model, //
                            UploadForm myUploadForm, RedirectAttributes redirectAttrs) {

        String description = myUploadForm.getDescription();
        System.out.println("Description: " + description);

        // Root Directory.
        String uploadRootPath = "/uploads/";
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = myUploadForm.getFileDatas();
        //
        List<File> uploadedFiles = new ArrayList<File>();
        List<String> failedFiles = new ArrayList<String>();

        for (MultipartFile fileData : fileDatas) {
            String name;
            // Client File Name

                name = request.getParameter("id") + ".jpg";


            System.out.println("Client File Name = " + name);


            if (name != null && name.length() > 0) {
                try {
//                    String relativeWebPath = "/uploads/";
//                    String absoluteFilePath = context.getRealPath(relativeWebPath);
                    FileUtils.deleteQuietly(new File("uploads/" + name));
                    File serverFile = new File("uploads/" + name);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                    uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile);
                    redirectAttrs.addFlashAttribute("message", "The image has been uploaded.");
                    Employee employeeWithPic = employees.findById(Integer.parseInt(request.getParameter("id"))).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + request.getParameter("id")));
                    employeeWithPic.setHasImage(true);
                    employees.save(employeeWithPic);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    failedFiles.add(name);
                }
            }
        }


//        model.addAttribute("description", description);
//        model.addAttribute("uploadedFiles", uploadedFiles);
//        model.addAttribute("failedFiles", failedFiles);
//        return "uploadResult";
            return ("redirect:/employee/" + request.getParameter("id"));
    }

}

