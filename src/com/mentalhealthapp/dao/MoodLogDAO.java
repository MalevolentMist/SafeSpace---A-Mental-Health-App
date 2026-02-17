package com.mentalhealthapp.dao;

import com.mentalhealthapp.model.MoodLog;
import com.mentalhealthapp.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MoodLogDAO {
    
    public boolean addMoodLog(MoodLog moodLog) {
        String sql = "INSERT INTO MOODLOG (SEID, SCORE, DATE, TIME) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, moodLog.getSeid());
            pstmt.setInt(2, moodLog.getScore());
            pstmt.setDate(3, Date.valueOf(moodLog.getDate()));
            pstmt.setTime(4, Time.valueOf(moodLog.getTime()));
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<MoodLog> getMoodLogsBySeekerId(int seid) {
        List<MoodLog> moodLogs = new ArrayList<>();
        String sql = "SELECT * FROM MOODLOG WHERE SEID = ? ORDER BY DATE DESC, TIME DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seid);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                MoodLog moodLog = new MoodLog();
                moodLog.setLogId(rs.getInt("LOGID"));
                moodLog.setSeid(rs.getInt("SEID"));
                moodLog.setScore(rs.getInt("SCORE"));
                moodLog.setDate(rs.getDate("DATE").toLocalDate());
                moodLog.setTime(rs.getTime("TIME").toLocalTime());
                moodLogs.add(moodLog);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return moodLogs;
    }
    
    public double getAverageMoodScore(int seid) {
        String sql = "SELECT AVG(SCORE) as avg_score FROM MOODLOG WHERE SEID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("avg_score");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0.0;
    }
    
    public int getCriticalMoodCount(int seid) {
        String sql = "SELECT COUNT(*) as count FROM MOODLOG WHERE SEID = ? AND SCORE <= 3";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("count");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}