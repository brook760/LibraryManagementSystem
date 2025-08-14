package com.Library.UI;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;

import com.Library.Entity.Student;
import com.Library.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AddStudentForm extends JInternalFrame  {
    private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public AddStudentForm() {
    	
    	setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        
        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(123, 31, 162)); 
        JLabel header = new JLabel("Add New Student", JLabel.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(header, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblID = new JLabel("ID:");
        JTextField IDField = new JTextField(20);

        JLabel lblName = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel lblEmail = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        JLabel lblRollNo = new JLabel("Roll No:");
        JTextField rollNoField = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblID, gbc);
        gbc.gridx = 1;
        formPanel.add(IDField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblName, gbc);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(lblEmail, gbc);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(lblRollNo, gbc);
        gbc.gridx = 1;
        formPanel.add(rollNoField, gbc);

        add(formPanel, BorderLayout.CENTER);
        
        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton saveBtn = new JButton("Save");
        saveBtn.setBackground(new Color(76, 175, 80)); 
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(244, 67, 54)); 
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====
        saveBtn.addActionListener(e -> {
        	int ID = Integer.parseInt(IDField.getText().trim());
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String rollNo = rollNoField.getText().trim();

            Student student = new Student();
            student.setId(ID);
            student.setName(name);
            student.setEmail(email);
            student.setRollNo(rollNo);

            Transaction tx = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                tx = session.beginTransaction();
                session.save(student);
                tx.commit();
                JOptionPane.showMessageDialog(this, "Student added successfully!");
                dispose();
            } catch (Exception ex) {
                if (tx != null) tx.rollback();
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to save student.");
            }
        });

        cancelBtn.addActionListener(e -> dispose());
        
     // ===== MAXIMIZE AFTER LOADED =====
        SwingUtilities.invokeLater(() -> {
            try {
                setMaximum(true);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        });

        setVisible(true);
    }
}