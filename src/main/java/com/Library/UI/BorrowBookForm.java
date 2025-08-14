package com.Library.UI;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Library.Entity.Book;
import com.Library.Entity.Borrowing;
import com.Library.Entity.Student;
import com.Library.Util.HibernateUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

public class BorrowBookForm extends JInternalFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public BorrowBookForm() {
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

        setSize(1045, 680);
        setLayout(new BorderLayout(15, 15));
		
        // ===== HEADER BAR =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(123, 31, 162)); // Purple theme
        JLabel header = new JLabel("Borrow Book", JLabel.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        header.setForeground(Color.WHITE);
        headerPanel.add(header, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

     // ===== FORM =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel titleLabel = new JLabel("Book ID:");
        JTextField bookIdField = new JTextField(20);

        JLabel IDLabel =new JLabel("Student ID:");
        JTextField studentIdField = new JTextField(20);
        
        JLabel Qty =new JLabel("Quantity to Borrow:");
        JTextField qtyField = new JTextField(20);

        // Row 0
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(bookIdField, gbc);
        
        // Row 1 - Author Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(IDLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(studentIdField, gbc);
        
        // Row 2 - Category
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(Qty, gbc);
        gbc.gridx = 1;
        formPanel.add(qtyField, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JButton borrowBtn = new JButton("Borrow");
        borrowBtn.setBackground(new Color(76, 175, 80)); // Green
        borrowBtn.setForeground(Color.WHITE);
        borrowBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(244, 67, 54)); // Red
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        buttonPanel.add(borrowBtn);
        buttonPanel.add(cancelBtn);
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        // ===== ACTIONS =====
        borrowBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
            	String bookIdText = bookIdField.getText().trim();
            	String studentIdText = studentIdField.getText().trim();
                String qtyText = qtyField.getText().trim();

                if (bookIdText.isEmpty() || studentIdText.isEmpty() || qtyText.isEmpty()) 
                {
                    JOptionPane.showInputDialog(this, "Please fill in all fields.");
                    return;
                }

                try 
                {
                    int bookId = Integer.parseInt(bookIdText);
                    int studentId = Integer.parseInt(studentIdText);
                    int borrowQty = Integer.parseInt(qtyText);

                    try (Session session = HibernateUtil.getSessionFactory().openSession()) 
                    {
                        Transaction tx = session.beginTransaction();
                        Book book = session.get(Book.class, bookId);
                        Student student = session.get(Student.class, studentId);

                        if (book == null) 
                        {
                            JOptionPane.showInputDialog(this, "Book not found with ID: " + bookId);
                        } else if (student == null) 
                        {
                            JOptionPane.showMessageDialog(BorrowBookForm.this, 
                                    "Student not found with ID: " + studentId);
                        } else 
                        {
                        	// Calculate available stock from total - borrowed
                            Long currentlyBorrowed = (Long) session.createQuery(
                                    "SELECT SUM(b.quantity) FROM Borrowing b " +
                                    "WHERE b.book.id = :bookId " +
                                    "AND b.student.id = :studentId ")
                                    .setParameter("bookId", bookId)
                                    .setParameter("studentId", studentId)
                                    .uniqueResult();
                            int availableStock = book.getQuantity() - 
                            		(currentlyBorrowed != null ? currentlyBorrowed.intValue() : 0);
                            
                            if (availableStock < borrowQty) 
                            {
                                JOptionPane.showMessageDialog(BorrowBookForm.this, 
                                        "Not enough quantity available. Available: " + availableStock);
                            } else 
                            {
                                // Create new borrowing transaction
                                Borrowing borrowing = new Borrowing();
                                borrowing.setBook(book);
                                borrowing.setStudent(student);
                                borrowing.setQuantity(borrowQty);

                                session.persist(borrowing);
                                
                                // ---- 2️⃣ Update Book available quantity ----
                                book.setQuantity(book.getQuantity() - borrowQty);
                                session.update(book);
                                
                                tx.commit();

                                JOptionPane.showMessageDialog(BorrowBookForm.this, 
                                        "✅ " + student.getName() + " borrowed " + borrowQty + 
                                        " copy(ies) of \"" + book.getName() + "\".");
                                dispose();
                  
                            }
                        }
                    }
                } catch (NumberFormatException ex) 
                {
                    JOptionPane.showInputDialog(this, "Book Name and Quantity must be numbers.");
                } catch (Exception ex) 
                {
                    ex.printStackTrace();
                    JOptionPane.showInputDialog(this, "Error: " + ex.getMessage());
                }
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
        borrowBtn.addActionListener(e -> {
            
        });

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


