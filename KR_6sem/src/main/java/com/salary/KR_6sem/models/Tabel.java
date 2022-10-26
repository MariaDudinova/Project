package com.salary.KR_6sem.models;
import javax.persistence.*;

@Entity
public class Tabel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tabel_id;
//    private String job;

    private int work_days, ill, chill;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id")
    private Workers worker_id;

    public Long getTabel_id() {
        return tabel_id;
    }

    public void setTabel_id(Long tabel_id) {
        this.tabel_id = tabel_id;
    }

    public int getWork_days() {
        return work_days;
    }

    public void setWork_days(int work_days) {
        this.work_days = work_days;
    }

    public int getIll() {
        return ill;
    }

    public void setIll(int ill) {
        this.ill = ill;
    }

    public int getChill() {
        return chill;
    }

    public void setChill(int chill) {
        this.chill = chill;
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

    public Tabel() {
    }

    public Tabel(Long tabel_id, int work_days, int ill, int chill, Workers worker_id) {
        this.tabel_id = tabel_id;
        this.work_days = work_days;
        this.ill = ill;
        this.chill = chill;
        this.worker_id = worker_id;
    }
}
