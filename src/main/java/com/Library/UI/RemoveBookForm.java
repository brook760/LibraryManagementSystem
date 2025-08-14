package com.Library.UI;

import javax.swing.*;
import java.util.List;
import org.hibernate.*;
import org.hibernate.query.Query;

import com.Library.Entity.Book;
import com.Library.Util.HibernateUtil;

import java.awt.*;
import java.beans.PropertyVetoException;

public class RemoveBookForm extends JInternalFrame  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("deprecation")
	public RemoveBookForm() {
		
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        
        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(123, 31, 162)); // Purple like dashboard
        JLabel header = new JLabel("❌ Remove Book", JLabel.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(header, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        
        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label + Field
        JLabel idLabel = new JLabel("Enter Book name to remove:");
        JTextField idField = new JTextField(20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(idLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        
        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JButton removeBtn = new JButton("Remove");
        removeBtn.setBackground(new Color(244, 67, 54)); // red
        removeBtn.setForeground(Color.WHITE);
        removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(158, 158, 158)); // grey
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        buttonPanel.add(removeBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

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
        
     // ===== MAXIMIZE AFTER ADDED =====
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
