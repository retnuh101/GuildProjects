/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hlschuler.classroster.service;

import dao.ClassRosterAuditDao;
import dao.ClassRosterDao;
import dao.ClassRosterPersistenceException;
import dto.Student;
import java.util.List;
import ui.ClassRosterView;

/**
 *
 * @author hschuler2992
 */
public class ClassRosterServiceLayerImpl implements ClassRosterServiceLayer{

    private ClassRosterAuditDao auditDao;
    
    ClassRosterDao dao;
    private ClassRosterServiceLayer service;
    private ClassRosterView view;
    
    public ClassRosterServiceLayerImpl(ClassRosterDao dao, ClassRosterAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    public ClassRosterServiceLayerImpl(ClassRosterServiceLayer service, ClassRosterView 
    view){
        this.service = service;
        this.view = view;
    }
    
    @Override
    public void createStudent(Student student) throws 
            ClassRosterDuplicateIdException, 
            ClassRosterDataValidationException, 
            ClassRosterPersistenceException {
        
        if (dao.getStudent(student.getStudentID()) != null){
        throw new UnsupportedOperationException(
                "ERROR: Could not create student. Student Id "
                + student.getStudentID()
                + " already exists.");
        }
        validateStudentData(student);
        dao.addStudent(student.getStudentID(), student);
        
        auditDao.writeAuditEntry("Student " + student.getStudentID() + " CREATED.");
    }

    @Override
    public List<Student> getAllStudents() throws ClassRosterPersistenceException {
        return dao.getAllStudents(); 
    }

    @Override
    public Student getStudent(String studentId) throws ClassRosterPersistenceException {
        return dao.getStudent(studentId); 
    }

    @Override
    public Student removeStudent(String studentId) throws ClassRosterPersistenceException {
        Student removedStudent = dao.removeStudent(studentId);
        auditDao.writeAuditEntry("Student " + studentId + " REMOVED");
        return dao.removeStudent(studentId); 
    }
    
    private void validateStudentData(Student student) throws
            ClassRosterDataValidationException{
        if (student.getFirstName() == null
                || student.getFirstName().trim().length() == 0
                || student.getLastName() == null
                || student.getLastName().trim().length() == 0
                || student.getCohort() == null
                || student.getCohort().trim().length() == 0){
            throw new ClassRosterDataValidationException(
                "ERROR: All fields [First Name, Last Name, Cohort] are required.");
        }
            
    }
}
