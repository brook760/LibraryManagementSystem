package com.Library.UI;

import javax.swing.*;
import java.awt.*;
import com.Library.Entity.Student;
import com.Library.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AddStudentForm extends JFrame {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("deprecation")
	public AddStudentForm() {
        setTitle("Add New Student");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));
        
        add(new JLabel("ID:"));
        JTextField IDField = new JTextField();
        add(IDField);

        add(new JLabel("Name:"));
        JTextField nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        JTextField emailField = new JTextField();
        add(emailField);

        add(new JLabel("Roll No:"));
        JTextField rollNoField = new JTextField();
        add(rollNoField);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        add(saveBtn);
        add(cancelBtn);

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

        setVisible(true);
    }
}