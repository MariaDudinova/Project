package com.salary.KR_6sem.models;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Workers{

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_w;

    @ManyToOne (optional=false)
    @JoinColumn (name = "job_id")
    private Post job_id;

    @OneToOne(mappedBy = "worker_id")
    private Tabel tabel;

    private String name, username, password;
    private boolean benefit;

    public Long getId() {
        return id_w;
    }

    public void setId(Long id_w) {
        this.id_w = id_w;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//`
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJob() {
        return job_id.getJob();
    }

    public int getMoney(){return job_id.getMoney();}

    public Post getPost() { return job_id; }


    public void setJob(Post job_id) {
        this.job_id = job_id;
    }


    public Long getTabel() {
        return tabel.getTabel_id();
    }

    public void setTabel(Tabel tabel) {
        this.tabel = tabel;
    }

    public boolean isBenefit() {
        return benefit;
    }

    public void setBenefit(boolean benefit) {
        this.benefit = benefit;
    }

    public Workers() {}

    public Workers(String name, String username, String password, boolean benefit, Post job_id) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.benefit = benefit;
        this.job_id = job_id;
    }
}
