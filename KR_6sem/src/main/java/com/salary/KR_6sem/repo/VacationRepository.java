package com.salary.KR_6sem.repo;

import com.salary.KR_6sem.models.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
    List<Vacation> findByMonth(int month);

}
