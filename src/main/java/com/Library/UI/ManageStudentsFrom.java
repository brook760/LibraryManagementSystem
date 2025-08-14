package com.Library.UI;
import com.Library.Entity.Student;
import com.Library.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.List;

public class ManageStudentsFrom extends JInternalFrame  {
	
	private static final long serialVersionUID = 6630849947067929041L;
	
	private JTable studentTable;
    private DefaultTableModel model;

    @SuppressWarnings("serial")
	public ManageStudentsFrom() {
    	setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        
        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(123, 31, 162));  // Purple color
        JLabel headerLabel = new JLabel("Manage Students", JLabel.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(headerLabel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Roll No"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        studentTable = new JTable(model);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.setRowHeight(25);
        studentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton cancelBtn = new JButton("Close"); // NEW Cancel Button
        
        editBtn.setBackground(new Color(76, 175, 80));  // Green for edit
        editBtn.setForeground(Color.WHITE);
        editBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        deleteBtn.setBackground(new Color(244, 67, 54));  // Red for delete
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        cancelBtn.setBackground(new Color(158, 158, 158)); // Grey for cancel
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load Data
        loadStudents();

        // Button actions
        editBtn.addActionListener(e -> editStudent());
        deleteBtn.addActionListener(e -> deleteStudent());
        cancelBtn.addActionListener(e -> dispose());
        
        // Maximize internal frame after added
        SwingUtilities.invokeLater(() -> {
            try {
                setMaximum(true);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        });

        setSize(900, 600);
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