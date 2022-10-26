package com.salary.KR_6sem.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Taxes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_tax;

    private String name;
    private double percent;

    public Long getId_tax() {
        return id_tax;
    }

    public void setId_tax(Long id_tax) {
        this.id_tax = id_tax;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Taxes() {
    }

    public Taxes(Long id_tax, String name, double percent) {
        this.id_tax = id_tax;
        this.name = name;
        this.percent = percent;
    }
}
