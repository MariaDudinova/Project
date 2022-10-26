package com.salary.KR_6sem.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Charity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_charity;

    private String name;
    private double sum;


    public Long getId_charity() {
        return id_charity;
    }

    public void setId_charity(Long id_charity) {
        this.id_charity = id_charity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}

