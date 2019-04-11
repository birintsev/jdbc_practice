package ua.edu.sumdu.nc.entities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class Employee {

    private String ename;
    private String job;
    private int depno;
    private int mgr;
    private double sal;
    private double comm;
    private LocalDate hiredate;

    public Employee() {
    }

    public Employee(String ename, String job, int depno, int mgr, double sal, double comm, LocalDate hidate) {
        this.ename = ename;
        this.job = job;
        this.depno = depno;
        this.mgr = mgr;
        this.sal = sal;
        this.comm = comm;
        this.hiredate = hidate;
    }

    public LocalDate getHiredate() {
        return hiredate;
    }

    public Employee setHiredate(LocalDate hiredate) {
        this.hiredate = hiredate;
        return this;
    }

    public String getEname() {
        return ename;
    }

    public Employee setEname(String ename) {
        this.ename = ename;
        return this;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getDepno() {
        return depno;
    }

    public void setDepno(int depno) {
        this.depno = depno;
    }

    public int getMgr() {
        return mgr;
    }

    public void setMgr(int mgr) {
        this.mgr = mgr;
    }

    public double getSal() {
        return sal;
    }

    public void setSal(double sal) {
        this.sal = sal;
    }

    public double getComm() {
        return comm;
    }

    public void setComm(double comm) {
        this.comm = comm;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", depno=" + depno +
                ", mgr=" + mgr +
                ", sal=" + sal +
                ", comm=" + comm +
                ", hiredate=" + hiredate +
                '}';
    }
}