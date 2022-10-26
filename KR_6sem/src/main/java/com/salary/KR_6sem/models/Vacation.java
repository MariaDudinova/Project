package com.salary.KR_6sem.models;

import javax.persistence.*;

@Entity
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_vac;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id")
    private Workers worker_id;

    private int month;

    public Long getId_vac() {
        return id_vac;
    }

    public void setId_vac(Long id_vac) {
        this.id_vac = id_vac;
    }

    public Long getWorker_id() {
        return worker_id.getId();
    }

    public String getWorkerName() {
        return worker_id.getName();
    }

    public void setWorker_id(Workers worker_id) {
        this.worker_id = worker_id;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Vacation(Workers worker_id, int month) {
        this.worker_id = worker_id;
        this.month = month;
    }

    public Vacation(){}
}

