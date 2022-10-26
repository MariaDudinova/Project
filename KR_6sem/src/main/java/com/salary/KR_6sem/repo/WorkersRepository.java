package com.salary.KR_6sem.repo;

import com.salary.KR_6sem.models.Workers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkersRepository extends JpaRepository<Workers, Long> {

    Workers findByUsername(String username);
    List<Workers> findByName(String name);
    List<Workers> findByBenefit(boolean benefit);

}