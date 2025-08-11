package com.Library.UI;
import com.Library.Entity.Student;
import com.Library.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ManageStudentsFrom extends JFrame {
	
	private static final long serialVersionUID = 6630849947067929041L;
	
	private JTable studentTable;
    private DefaultTableModel model;

    public ManageStudentsFrom() {
        setTitle("Manage Students");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Roll No"}, 0);
        studentTable = new JTable(model);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        JButton editBtn = new JButton("✏ Edit");
        JButton deleteBtn = new JButton("🗑 Delete");
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Data
        loadStudents();

        // Edit button action
        editBtn.addActionListener(e -> editStudent());

        // Delete button action
        deleteBtn.addActionListener(e -> deleteStudent());

        setVisible(true);
    }

    private void loadStudents() {
        model.setRowCount(0);
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Student> query = session.createQuery("from Student", Student.class);
            List<Student> students = query.list();

            for (Student s : students) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getName(),
                        s.getEmail(),
                        s.getRollNo()
                });
            }
        }
    }

    @SuppressWarnings("deprecation")
	private void editStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to edit.");
            return;
        }

        // Get selected data
        int id = (int) model.getValueAt(selectedRow, 0);
        String name = (String) model.getValueAt(selectedRow, 1);
        String email = (String) model.getValueAt(selectedRow, 2);
        String rollNo = (String) model.getValueAt(selectedRow, 3);

        // Input new values
        name = JOptionPane.showInputDialog(this, "Edit Name:", name);
        email = JOptionPane.showInputDialog(this, "Edit Email:", email);
        rollNo = JOptionPane.showInputDialog(this, "Edit Roll No:", rollNo);

        // Save to DB
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Student s = session.get(Student.class, id);
            s.setName(name);
            s.setEmail(email);
            s.setRollNo(rollNo);
            session.update(s);
            tx.commit();
        }

        loadStudents();
    }

    @SuppressWarnings("deprecation")
	private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this student?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) model.getValueAt(selectedRow, 0);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Student s = session.get(Student.class, id);
            if (s != null) {
                session.delete(s);
            }
            tx.commit();
        }

        loadStudents();
    }

    public static void main(String[] args) {
        new ManageStudentsFrom();
    }
}