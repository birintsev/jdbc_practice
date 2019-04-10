package ua.edu.sumdu.nc.entities;

public class Employee {

    private String firstname;
    private String lastname;
    private String job;
    private int depno;
    private int mgr;
    private double sal;
    private double comm;

    public Employee() {
    }

    public Employee(String firstname, String lastname, String job, int depno, int mgr, double sal, double comm) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.job = job;
        this.depno = depno;
        this.mgr = mgr;
        this.sal = sal;
        this.comm = comm;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
}
