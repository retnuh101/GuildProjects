package com.srmsolutions.repos;

import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.EmployeeOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeOfWeekRepository extends JpaRepository<EmployeeOfWeek, Integer> {
    EmployeeOfWeek findFirstByOrderByIdDesc();
    List<EmployeeOfWeek> findAllByEmployee(Employee employee);
    void deleteAllByEmployee(Employee employee);
    EmployeeOfWeek findFirstByWeekStarting(LocalDate localDate);

}
