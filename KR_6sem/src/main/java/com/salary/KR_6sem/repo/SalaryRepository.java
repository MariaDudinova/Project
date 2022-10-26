package com.salary.KR_6sem.repo;

import com.salary.KR_6sem.models.Salary;
//import com.salary.KR_6sem.models.Workers;
import com.salary.KR_6sem.models.Workers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SalaryRepository  extends JpaRepository<Salary, Long> {

}