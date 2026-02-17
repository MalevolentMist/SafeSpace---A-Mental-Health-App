package com.mentalhealthapp.model;

import java.time.LocalDateTime;

public class Alert {
    private int alid;
    private int logId;
    private String emergencyContact;
    private String status;
    private LocalDateTime createdAt;
    
    public Alert() {}
    
    public Alert(int logId, String emergencyContact, String status) {
        this.logId = logId;
        this.emergencyContact = emergencyContact;
        this.status = status;
    }
    
    public Alert(int alid, int logId, String emergencyContact, String status, LocalDateTime createdAt) {
        this.alid = alid;
        this.logId = logId;
        this.emergencyContact = emergencyContact;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    public int getAlid() {
        return alid;
    }
    
    public void setAlid(int alid) {
        this.alid = alid;
    }
    
    public int getLogId() {
        return logId;
    }
    
    public void setLogId(int logId) {
        this.logId = logId;
    }
    
    public String getEmergencyContact() {
        return emergencyContact;
    }
    
    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Alert{" +
                "alid=" + alid +
                ", logId=" + logId +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}