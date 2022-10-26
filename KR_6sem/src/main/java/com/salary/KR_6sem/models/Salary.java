package com.salary.KR_6sem.models;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long salary_id;
//    private String job;

    private double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id")
    private Workers worker_id;

    public Salary() {
    }

    public Salary(Workers worker_id, double salary) {
        this.salary = salary;
        this.worker_id = worker_id;
    }

    public Long getSalary_id() {
        return salary_id;
    }

    public void setSalary_id(Long salary_id) {
        this.salary_id = salary_id;
    }

    public Workers getWorker() {
        return worker_id;
    }

    public String getWorker_id() {
        return worker_id.getName();
    }

    public void setWorker_id(Workers worker_id) {
        this.worker_id = worker_id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
