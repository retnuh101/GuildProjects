package com.srmsolutions.repos;

import com.srmsolutions.entities.Category;
import com.srmsolutions.entities.Employee;
import com.srmsolutions.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findByCategoryListContaining (Category category);
    List<Event> findByAttendingListContaining (Employee employee);
    List<Event> findByInviteListContaining (Employee employee);
    List<Event> findByStartTimeBetween(LocalDate start, LocalDate end);
    List<Event> findByIsPublicTrue();
}
