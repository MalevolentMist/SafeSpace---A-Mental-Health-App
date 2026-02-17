package com.mentalhealthapp.dao;

import com.mentalhealthapp.model.Counsellor;
import com.mentalhealthapp.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CounsellorDAO {
    
    public boolean registerCounsellor(Counsellor counsellor) {
        Connection conn = null;
        PreparedStatement pstmtPerson = null;
        PreparedStatement pstmtCounsellor = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            String sqlPerson = "INSERT INTO PERSON (NAME, CONTACT, PSWD) VALUES (?, ?, ?)";
            pstmtPerson = conn.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS);
            pstmtPerson.setString(1, counsellor.getName());
            pstmtPerson.setString(2, counsellor.getContact());
            pstmtPerson.setString(3, counsellor.getPassword());
            pstmtPerson.executeUpdate();
            
            ResultSet rs = pstmtPerson.getGeneratedKeys();
            int pid = 0;
            if (rs.next()) {
                pid = rs.getInt(1);
            }
            
            String sqlCounsellor = "INSERT INTO COUNSELLOR (PID, EMAIL, SPEC, LICNO) VALUES (?, ?, ?, ?)";
            pstmtCounsellor = conn.prepareStatement(sqlCounsellor);
            pstmtCounsellor.setInt(1, pid);
            pstmtCounsellor.setString(2, counsellor.getEmail());
            pstmtCounsellor.setString(3, counsellor.getSpecialization());
            pstmtCounsellor.setString(4, counsellor.getLicenseNumber());
            pstmtCounsellor.executeUpdate();
            
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
                if (pstmtCounsellor != null) pstmtCounsellor.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Counsellor loginCounsellor(String contact, String password) {
        String sql = "SELECT P.PID, P.NAME, P.CONTACT, P.PSWD, C.CID, C.EMAIL, C.SPEC, C.LICNO " +
                     "FROM PERSON P " +
                     "INNER JOIN COUNSELLOR C ON P.PID = C.PID " +
                     "WHERE P.CONTACT = ? AND P.PSWD = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, contact);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Counsellor counsellor = new Counsellor();
                counsellor.setPid(rs.getInt("PID"));
                counsellor.setCid(rs.getInt("CID"));
                counsellor.setName(rs.getString("NAME"));
                counsellor.setContact(rs.getString("CONTACT"));
                counsellor.setPassword(rs.getString("PSWD"));
                counsellor.setEmail(rs.getString("EMAIL"));
                counsellor.setSpecialization(rs.getString("SPEC"));
                counsellor.setLicenseNumber(rs.getString("LICNO"));
                return counsellor;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Counsellor> getAllCounsellors() {
        List<Counsellor> counsellors = new ArrayList<>();
        String sql = "SELECT P.PID, P.NAME, P.CONTACT, P.PSWD, C.CID, C.EMAIL, C.SPEC, C.LICNO " +
                     "FROM PERSON P " +
                     "INNER JOIN COUNSELLOR C ON P.PID = C.PID";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Counsellor counsellor = new Counsellor();
                counsellor.setPid(rs.getInt("PID"));
                counsellor.setCid(rs.getInt("CID"));
                counsellor.setName(rs.getString("NAME"));
                counsellor.setContact(rs.getString("CONTACT"));
                counsellor.setPassword(rs.getString("PSWD"));
                counsellor.setEmail(rs.getString("EMAIL"));
                counsellor.setSpecialization(rs.getString("SPEC"));
                counsellor.setLicenseNumber(rs.getString("LICNO"));
                counsellors.add(counsellor);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return counsellors;
    }
    
    public Counsellor getCounsellorById(int cid) {
        String sql = "SELECT P.PID, P.NAME, P.CONTACT, P.PSWD, C.CID, C.EMAIL, C.SPEC, C.LICNO " +
                     "FROM PERSON P " +
                     "INNER JOIN COUNSELLOR C ON P.PID = C.PID " +
                     "WHERE C.CID = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cid);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Counsellor counsellor = new Counsellor();
                counsellor.setPid(rs.getInt("PID"));
                counsellor.setCid(rs.getInt("CID"));
                counsellor.setName(rs.getString("NAME"));
                counsellor.setContact(rs.getString("CONTACT"));
                counsellor.setPassword(rs.getString("PSWD"));
                counsellor.setEmail(rs.getString("EMAIL"));
                counsellor.setSpecialization(rs.getString("SPEC"));
                counsellor.setLicenseNumber(rs.getString("LICNO"));
                return counsellor;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}