package com.Library.UI;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Library.Entity.Book;
import com.Library.Util.HibernateUtil;

import java.awt.*;

public class BorrowBookForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public BorrowBookForm() {
        setTitle("Borrow Book");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Book ID:"));
        JTextField bookIdField = new JTextField();
        add(bookIdField);

        add(new JLabel("Student ID:"));
        JTextField studentIdField = new JTextField();
        add(studentIdField);
        
        add(new JLabel("Quantity to Borrow:"));
        JTextField qtyField = new JTextField();
        add(qtyField);

        JButton borrowBtn = new JButton("Borrow");
        JButton cancelBtn = new JButton("Cancel");
        add(borrowBtn);
        add(cancelBtn);
        
        cancelBtn.addActionListener(e -> dispose());

        borrowBtn.addActionListener(e -> {
            String bookIdText = bookIdField.getText().trim();
            String borrower = studentIdField.getText().trim();
            String qtyText = qtyField.getText().trim();

            if (bookIdText.isEmpty() || borrower.isEmpty() || qtyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                int bookId = Integer.parseInt(bookIdText);
                int borrowQty = Integer.parseInt(qtyText);

                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    Transaction tx = session.beginTransaction();
                    Book book = session.get(Book.class, bookId);

                    if (book == null) {
                        JOptionPane.showMessageDialog(this, "Book not found with ID: " + bookId);
                    } else if (book.getQuantity() < borrowQty) {
                        JOptionPane.showMessageDialog(this, "Not enough quantity available.");
                    } else {
                        book.setQuantity(book.getQuantity() - borrowQty);
                        session.update(book);
                        tx.commit();

                        JOptionPane.showMessageDialog(this, borrower + " borrowed " + borrowQty + " copy(ies) of \"" + book.getName() + "\".");
                        dispose();
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Book Name and Quantity must be numbers.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}

