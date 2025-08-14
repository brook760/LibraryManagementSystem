package com.Library.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.beans.PropertyVetoException;
import java.util.List;
import com.Library.Entity.Book;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.Library.Util.HibernateUtil;


public class ViewBooksForm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
    private DefaultTableModel tableModel;
	
	@SuppressWarnings("serial")
	public ViewBooksForm() {
		
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        
        // ===== HEADER =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(123, 31, 162)); // purple like dashboard
        JLabel header = new JLabel("📚 View All Books", JLabel.CENTER);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 26));
        headerPanel.add(header, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] columnNames = {"ID", "Title", "Authors", "Categories", "Publishers", "Quantity"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table read-only
            }
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        add(scrollPane, BorderLayout.CENTER);
        
    	// ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnCancel = new JButton("✖ Close");
        btnCancel.setBackground(new Color(244, 67, 54)); // red
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        btnCancel.addActionListener(e -> dispose());

        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // ===== LOAD DATA =====
        loadBooks();

        // ===== MAXIMIZE AFTER ADDED =====
        SwingUtilities.invokeLater(() -> {
            try {
                setMaximum(true);
            } catch (PropertyVetoException e) {
                e.printStackTrace();
            }
        });

        setVisible(true);
    }

	private void loadBooks() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = null;
        Transaction tx = null;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();

            // Use join fetch to avoid LazyInitializationException
            List<Book> books = session.createQuery(
                "SELECT DISTINCT b FROM Book b " +
                "LEFT JOIN FETCH b.authors " +
                "LEFT JOIN FETCH b.categories " +
                "LEFT JOIN FETCH b.publishers",
                Book.class
            ).getResultList();

            tx.commit();

            for (Book book : books) {
                String authors = (book.getAuthors() != null && !book.getAuthors().isEmpty())
                        ? String.join(", ", book.getAuthors().stream().map(a -> a.getName()).toList())
                        : "";
                String categories = (book.getCategories() != null && !book.getCategories().isEmpty())
                        ? String.join(", ", book.getCategories().stream().map(c -> c.getName()).toList())
                        : "";
                String publishers = (book.getPublishers() != null && !book.getPublishers().isEmpty())
                        ? String.join(", ", book.getPublishers().stream().map(p -> p.getName()).toList())
                        : "";

                tableModel.addRow(new Object[]{
                        book.getId(),
                        book.getName(),
                        authors,
                        categories,
                        publishers,
                        book.getQuantity()
                });
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Failed to load books: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }
}
