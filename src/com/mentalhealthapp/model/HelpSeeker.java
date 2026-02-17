package com.mentalhealthapp.model;

public class HelpSeeker extends Person {
    private int seid;
    private String emergencyContact;
    
    public HelpSeeker() {
        super();
    }
    
    public HelpSeeker(String name, String contact, String password, String emergencyContact) {
        super(name, contact, password);
        this.emergencyContact = emergencyContact;
    }
    
    public HelpSeeker(int pid, int seid, String name, String contact, String password, String emergencyContact) {
        super(pid, name, contact, password);
        this.seid = seid;
        this.emergencyContact = emergencyContact;
    }
    
    public int getSeid() {
        return seid;
    }
    
    public void setSeid(int seid) {
        this.seid = seid;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    @Override
    public String toString() {
        return "HelpSeeker{" +
                "seid=" + seid +
                ", name='" + getName() + '\'' +
                ", contact='" + getContact() + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                '}';
    }
}