package com.mentalhealthapp.dao;

import com.mentalhealthapp.model.HelpSeeker;
import com.mentalhealthapp.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HelpSeekerDAO {
    
    public boolean registerHelpSeeker(HelpSeeker helpSeeker) {
        Connection conn = null;
        PreparedStatement pstmtPerson = null;
        PreparedStatement pstmtHelpSeeker = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            String sqlPerson = "INSERT INTO PERSON (NAME, CONTACT, PSWD) VALUES (?, ?, ?)";
            pstmtPerson = conn.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS);
            pstmtPerson.setString(1, helpSeeker.getName());
            pstmtPerson.setString(2, helpSeeker.getContact());
            pstmtPerson.setString(3, helpSeeker.getPassword());
            pstmtPerson.executeUpdate();
            
            ResultSet rs = pstmtPerson.getGeneratedKeys();
            int pid = 0;
            if (rs.next()) {
                pid = rs.getInt(1);
            }
            
            String sqlHelpSeeker = "INSERT INTO HELPSEEKER (PID, ECONTACT) VALUES (?, ?)";
            pstmtHelpSeeker = conn.prepareStatement(sqlHelpSeeker);
            pstmtHelpSeeker.setInt(1, pid);
            pstmtHelpSeeker.setString(2, helpSeeker.getEmergencyContact());
            pstmtHelpSeeker.executeUpdate();
            
            conn.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmtPerson != null) pstmtPerson.close();
                if (pstmtHelpSeeker != null) pstmtHelpSeeker.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public HelpSeeker loginHelpSeeker(String contact, String password) {
        String sql = "SELECT P.PID, P.NAME, P.CONTACT, P.PSWD, H.SEID, H.ECONTACT " +
                     "FROM PERSON P " +
                     "INNER JOIN HELPSEEKER H ON P.PID = H.PID " +
                     "WHERE P.CONTACT = ? AND P.PSWD = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, contact);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                HelpSeeker helpSeeker = new HelpSeeker();
                helpSeeker.setPid(rs.getInt("PID"));
                helpSeeker.setSeid(rs.getInt("SEID"));
                helpSeeker.setName(rs.getString("NAME"));
                helpSeeker.setContact(rs.getString("CONTACT"));
                helpSeeker.setPassword(rs.getString("PSWD"));
                helpSeeker.setEmergencyContact(rs.getString("ECONTACT"));
                return helpSeeker;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public HelpSeeker getHelpSeekerById(int seid) {
        String sql = "SELECT P.PID, P.NAME, P.CONTACT, P.PSWD, H.SEID, H.ECONTACT " +
                     "FROM PERSON P " +
                     "INNER JOIN HELPSEEKER H ON P.PID = H.PID " +
                     "WHERE H.SEID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, seid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                HelpSeeker helpSeeker = new HelpSeeker();
                helpSeeker.setPid(rs.getInt("PID"));
                helpSeeker.setSeid(rs.getInt("SEID"));
                helpSeeker.setName(rs.getString("NAME"));
                helpSeeker.setContact(rs.getString("CONTACT"));
                helpSeeker.setPassword(rs.getString("PSWD"));
                helpSeeker.setEmergencyContact(rs.getString("ECONTACT"));
                return helpSeeker;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<HelpSeeker> getAllHelpSeekers() {
        List<HelpSeeker> helpSeekers = new ArrayList<>();
        String sql = "SELECT P.PID, P.NAME, P.CONTACT, P.PSWD, H.SEID, H.ECONTACT " +
                     "FROM PERSON P " +
                     "INNER JOIN HELPSEEKER H ON P.PID = H.PID";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                HelpSeeker helpSeeker = new HelpSeeker();
                helpSeeker.setPid(rs.getInt("PID"));
                helpSeeker.setSeid(rs.getInt("SEID"));
                helpSeeker.setName(rs.getString("NAME"));
                helpSeeker.setContact(rs.getString("CONTACT"));
                helpSeeker.setPassword(rs.getString("PSWD"));
                helpSeeker.setEmergencyContact(rs.getString("ECONTACT"));
                helpSeekers.add(helpSeeker);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return helpSeekers;
    }
}