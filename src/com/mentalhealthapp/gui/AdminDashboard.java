package com.mentalhealthapp.gui;

import com.mentalhealthapp.dao.*;
import com.mentalhealthapp.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminDashboard extends JFrame {
	private AlertDAO alertDAO = new AlertDAO();
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private CounsellorDAO counsellorDAO = new CounsellorDAO();
    
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
    }
    
    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Alerts", createAlertsPanel());
        tabbedPane.addTab("Appointments", createAppointmentsPanel());
        tabbedPane.addTab("Counsellors", createCounsellorsPanel());
        tabbedPane.addTab("Assign Counsellor", createAssignPanel());
        
        add(tabbedPane);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }
    
    private JPanel createAlertsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("🚨 Active Alerts", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.RED);
        
        String[] columns = {"Alert ID", "Log ID", "Emergency Contact", "Status", "Created At"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        
        loadAlerts(model);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        JButton refreshBtn = new JButton("Refresh");
        JButton resolveBtn = new JButton("Resolve Selected Alert");
        resolveBtn.setBackground(new Color(50, 205, 50));
        resolveBtn.setForeground(Color.WHITE);
        
        refreshBtn.addActionListener(e -> loadAlerts(model));
        resolveBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int alid = (Integer) table.getValueAt(selectedRow, 0);
                if (alertDAO.resolveAlert(alid)) {
                    JOptionPane.showMessageDialog(this, "Alert resolved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadAlerts(model);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an alert to resolve!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(resolveBtn);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadAlerts(DefaultTableModel model) {
        model.setRowCount(0);
        List<Alert> alerts = alertDAO.getActiveAlerts();
        
        for (Alert alert : alerts) {
            model.addRow(new Object[]{
                alert.getAlid(),
                alert.getLogId(),
                alert.getEmergencyContact(),
                alert.getStatus(),
                alert.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
            });
        }
    }
    
    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("All Appointments", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        String[] columns = {"ID", "SEID", "CID", "Date", "Time", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        
        loadAppointments(model);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadAppointments(model));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshBtn);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadAppointments(DefaultTableModel model) {
        model.setRowCount(0);
        List<Appointment> appointments = appointmentDAO.getAllAppointments();
        
        for (Appointment apt : appointments) {
            model.addRow(new Object[]{
                apt.getAid(),
                apt.getSeid(),
                apt.getCid() > 0 ? apt.getCid() : "Unassigned",
                apt.getDate(),
                apt.getTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                apt.getStatus()
            });
        }
    }
    
    private JPanel createCounsellorsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("All Counsellors", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        String[] columns = {"CID", "Name", "Email", "Specialization", "License"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        
        loadCounsellors(model);
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadCounsellors(model));
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(refreshBtn);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadCounsellors(DefaultTableModel model) {
        model.setRowCount(0);
        List<Counsellor> counsellors = counsellorDAO.getAllCounsellors();
        
        for (Counsellor c : counsellors) {
            model.addRow(new Object[]{
                c.getCid(),
                c.getName(),
                c.getEmail(),
                c.getSpecialization(),
                c.getLicenseNumber()
            });
        }
    }
    
    private JPanel createAssignPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        JLabel titleLabel = new JLabel("Assign Counsellor to Appointment");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        
        formPanel.add(new JLabel("Appointment ID:"));
        JTextField aidField = new JTextField();
        formPanel.add(aidField);
        
        formPanel.add(new JLabel("Counsellor ID:"));
        JTextField cidField = new JTextField();
        formPanel.add(cidField);
        
        JButton assignBtn = new JButton("Assign Counsellor");
        assignBtn.setFont(new Font("Arial", Font.BOLD, 14));
        assignBtn.setBackground(new Color(70, 130, 180));
        assignBtn.setForeground(Color.WHITE);
        assignBtn.addActionListener(e -> {
            try {
                int aid = Integer.parseInt(aidField.getText());
                int cid = Integer.parseInt(cidField.getText());
                
                if (appointmentDAO.assignCounsellor(aid, cid)) {
                    JOptionPane.showMessageDialog(this, "Counsellor assigned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    aidField.setText("");
                    cidField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to assign counsellor!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid IDs!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton viewPendingBtn = new JButton("View Pending");
        viewPendingBtn.addActionListener(e -> showPendingAppointments());
        
        formPanel.add(assignBtn);
        formPanel.add(viewPendingBtn);
        
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(formPanel);
        
        return panel;
    }
    
    private void showPendingAppointments() {
        List<Appointment> pending = appointmentDAO.getPendingAppointments();
        
        StringBuilder sb = new StringBuilder("Pending Appointments:\n\n");
        for (Appointment apt : pending) {
            sb.append("ID: ").append(apt.getAid())
              .append(" | Date: ").append(apt.getDate())
              .append(" | Time: ").append(apt.getTime())
              .append("\n");
        }
        
        JOptionPane.showMessageDialog(this, sb.toString(), "Pending Appointments", JOptionPane.INFORMATION_MESSAGE);
    }
}