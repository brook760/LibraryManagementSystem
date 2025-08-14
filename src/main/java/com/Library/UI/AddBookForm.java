package com.Library.UI;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Library.Entity.Author;
import com.Library.Entity.Book;
import com.Library.Entity.Category;
import com.Library.Entity.Publisher;
import com.Library.Util.HibernateUtil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.*;

public class AddBookForm extends JInternalFrame {

	private static final long serialVersionUID = 1L;

	public AddBookForm() {
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);

		// Size will get overridden when maximizing
        setSize(1045, 680);
        setLayout(new BorderLayout(15,15));
        
        // ====== HEADER ======
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(123, 31, 162));
        JLabel header = new JLabel("Add Book", JLabel.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerPanel.add(header, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);
        
     // ====== FORM ======
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel titleLabel = new JLabel("Book Title:");
        JTextField titleField = new JTextField(20);
        
        JLabel authorLabel = new JLabel("Author Name:");
        JTextField authorField = new JTextField(20);

        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField(20);

        JLabel publisherLabel = new JLabel("Publisher:");
        JTextField publisherField = new JTextField(20);

        JLabel qtyLabel = new JLabel("Quantity:");
        JTextField qtyField = new JTextField(20);

        // Row 0 - Book Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(titleLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);

        // Row 1 - Author Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(authorLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(authorField, gbc);

        // Row 2 - Category
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(categoryField, gbc);

        // Row 3 - Publisher
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(publisherLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(publisherField, gbc);

        // Row 4 - Quantity
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(qtyLabel, gbc);
        gbc.gridx = 1;
        formPanel.add(qtyField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // ====== BUTTON PANEL ======
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

        // Save Button Action
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    Transaction tx = session.beginTransaction();

                    // --- Create Author ---
                    Author author = new Author();
                    author.setName(authorField.getText());

                    // --- Create Category ---
                    Category category = new Category();
                    category.setName(categoryField.getText());

                    // --- Create Publisher ---
                    Publisher publisher = new Publisher();
                    publisher.setName(publisherField.getText());

                    // --- Create Book ---
                    Book book = new Book();
                    book.setName(titleField.getText());
                    book.setQuantity(Integer.parseInt(qtyField.getText()));
                    book.setAuthors(Set.of(author));
                    book.setCategories(Set.of(category));
                    book.setPublishers(Set.of(publisher));

                    // Set back-reference (important for bidirectional mapping)
                    author.setBooks(Arrays.asList(book));
                    category.setBooks(Arrays.asList(book));
                    publisher.setBooks(Arrays.asList(book));

                    // Save everything
                    session.persist(author);
                    session.persist(category);
                    session.persist(publisher);
                    session.persist(book);

                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Book saved successfully!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        cancelBtn.addActionListener(e -> dispose());

     // ====== MAXIMIZE AFTER LOADED ======
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

