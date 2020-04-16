package com.srmsolutions.repos;

import com.srmsolutions.entities.Category;
import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.Event;
import com.srmsolutions.entities.OrgGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrgGroupRepository extends JpaRepository<OrgGroup, Integer> {
    List<OrgGroup> findByEmployeeListContaining(Employee employee);
}
