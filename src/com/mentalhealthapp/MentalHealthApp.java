package com.mentalhealthapp;

import com.mentalhealthapp.gui.LoginFrame;
import com.mentalhealthapp.util.DatabaseConnection;

import javax.swing.*;

public class MentalHealthApp {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("==============================================");
        System.out.println("  Mental Health Support Platform - Starting");
        System.out.println("==============================================");
        
        if (!DatabaseConnection.testConnection()) {
            JOptionPane.showMessageDialog(null,
                "Failed to connect to database!\n" +
                "Please check your database configuration in DatabaseConnection.java",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}