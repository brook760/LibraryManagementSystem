package com.Library.UI;

import com.Library.Entity.Student;
import com.Library.Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class ViewStudentForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable studentTable;
    private DefaultTableModel model;

    public ViewStudentForm() {
        setTitle("View Students");
        setSize(700, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table model with column names
        model = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Roll No"}, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        studentTable = new JTable(model);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        loadStudents();

        setVisible(true);
    }

    private void loadStudents() {
        model.setRowCount(0); // Clear table before loading
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

    public static void main(String[] args) {
        new ViewStudentForm();
    }
}
