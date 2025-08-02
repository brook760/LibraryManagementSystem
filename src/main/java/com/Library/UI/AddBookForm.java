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
import java.util.*;

public class AddBookForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddBookForm() {
        setTitle("Add New Book");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Book Title:"));
        JTextField titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author Name:"));
        JTextField authorField = new JTextField();
        add(authorField);

        add(new JLabel("Category:"));
        JTextField categoryField = new JTextField();
        add(categoryField);

        add(new JLabel("Publisher:"));
        JTextField publisherField = new JTextField();
        add(publisherField);

        //add(new JLabel("ISBN:"));
        //JTextField isbnField = new JTextField();
        //add(isbnField);

        add(new JLabel("Quantity:"));
        JTextField qtyField = new JTextField();
        add(qtyField);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        add(saveBtn);
        add(cancelBtn);

     // Save Button Action
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                    Transaction tx =session.beginTransaction();
                    
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

                    //Save everything
                    session.persist(author);
                    session.persist(category);
                    session.persist(publisher);
                    session.persist(book);

                    tx.commit();
                    JOptionPane.showMessageDialog(null, "Book saved using BookRepo!");
                    dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        setVisible(true);
    }
}

