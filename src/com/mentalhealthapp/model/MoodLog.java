package com.mentalhealthapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class MoodLog {
    private int logId;
    private int seid;
    private int score;
    private LocalDate date;
    private LocalTime time;
    
    public MoodLog() {}
    
    public MoodLog(int seid, int score, LocalDate date, LocalTime time) {
        this.seid = seid;
        this.score = score;
        this.date = date;
        this.time = time;
    }
    
    public MoodLog(int logId, int seid, int score, LocalDate date, LocalTime time) {
        this.logId = logId;
        this.seid = seid;
        this.score = score;
        this.date = date;
        this.time = time;
    }
    
    public int getLogId() {
        return logId;
    }
    
    public void setLogId(int logId) {
        this.logId = logId;
    }
    
    public int getSeid() {
        return seid;
    }
    
    public void setSeid(int seid) {
        this.seid = seid;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    public String getStatus() {
        if (score <= 3) {
            return "Critical";
        } else if (score <= 6) {
            return "Moderate";
        } else {
            return "Good";
        }
    }
    
    @Override
    public String toString() {
        return "MoodLog{" +
                "logId=" + logId +
                ", seid=" + seid +
                ", score=" + score +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}