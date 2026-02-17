package com.mentalhealthapp.model;

public class Counsellor extends Person {
    private int cid;
    private String email;
    private String specialization;
    private String licenseNumber;
    
    public Counsellor() {
        super();
    }
    
    public Counsellor(String name, String contact, String password, String email, 
                     String specialization, String licenseNumber) {
        super(name, contact, password);
        this.email = email;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
    }
    
    public Counsellor(int pid, int cid, String name, String contact, String password, 
                     String email, String specialization, String licenseNumber) {
        super(pid, name, contact, password);
        this.cid = cid;
        this.email = email;
        this.specialization = specialization;
        this.licenseNumber = licenseNumber;
    }
    
    public int getCid() {
        return cid;
    }
    
    public void setCid(int cid) {
        this.cid = cid;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
    
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    
    @Override
    public String toString() {
        return "Counsellor{" +
                "cid=" + cid +
                ", name='" + getName() + '\'' +
                ", email='" + email + '\'' +
                ", specialization='" + specialization + '\'' +
                ", licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}