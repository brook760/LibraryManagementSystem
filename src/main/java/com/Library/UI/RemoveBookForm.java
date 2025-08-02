package com.Library.UI;

import javax.swing.*;
import java.util.List;
import org.hibernate.*;
import org.hibernate.query.Query;

import com.Library.Entity.Book;
import com.Library.Util.HibernateUtil;

import java.awt.*;

public class RemoveBookForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public RemoveBookForm() {
        setTitle("Remove Book");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 1, 10, 10));

        // Label + Field
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel idLabel = new JLabel("Enter Book ID to remove:");
        JTextField idField = new JTextField(10);
        inputPanel.add(idLabel);
        inputPanel.add(idField);
        add(inputPanel);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton removeBtn = new JButton("Remove");
        JButton cancelBtn = new JButton("Cancel");
        buttonPanel.add(removeBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel);

     	// Cancel action
        cancelBtn.addActionListener(e -> dispose());
        
        // Remove action
        removeBtn.addActionListener(e -> {
            String input = idField.getText().trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a book title.");
                return;
            }


            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            	Query<Book> query = session.createQuery(
                        "FROM Book WHERE name = :name", Book.class);
                    query.setParameter("name", input);

                    List<Book> books = query.getResultList();

                    if (books.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No book found with title: " + input);
                    } else {
                        for (Book book : books) {
                            int response = JOptionPane.showConfirmDialog(
                                this,
                                "Remove book: " + book.getName() + " (ID: " + book.getId() + ")?",
                                "Confirm Deletion",
                                JOptionPane.YES_NO_OPTION
                            );

                            if (response == JOptionPane.YES_OPTION) {
                                Transaction tx = session.beginTransaction();
                                session.delete(book);
                                tx.commit();
                                JOptionPane.showMessageDialog(this, "Book removed successfully!");
                            }
                        }
                        dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            });
        setVisible(true);
    }
}
