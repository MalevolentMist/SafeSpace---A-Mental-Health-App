package com.mentalhealthapp.model;

public class Person {
    private int pid;
    private String name;
    private String contact;
    private String password;
    
    public Person() {}
    
    public Person(String name, String contact, String password) {
        this.name = name;
        this.contact = contact;
        this.password = password;
    }
    
    public Person(int pid, String name, String contact, String password) {
        this.pid = pid;
        this.name = name;
        this.contact = contact;
        this.password = password;
    }
    
    public int getPid() {
        return pid;
    }
    
    public void setPid(int pid) {
        this.pid = pid;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getContact() {
        return contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "Person{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}