package com.Library.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
import com.Library.Entity.Book;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.Library.Util.HibernateUtil;


public class ViewBooksForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ViewBooksForm() {
        setTitle("View All Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] columnNames = {"ID", "Title", "Authors", "Categories", "Publisher", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        loadBooks(model);
        setVisible(true);
    }

   private void loadBooks(DefaultTableModel model) {
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
    	            "LEFT JOIN FETCH b.publishers", Book.class)
    	            .getResultList();
    	        
    	        tx.commit();
    	        
    	        for (Book book : books) {
    	        	String authors = book.getAuthors() != null
    	        			? String.join(", ", book.getAuthors().stream().map(a -> a.getName()).toList())
    	        					: "";
    	        	String categories = book.getCategories() != null
    	        			? String.join(", ", book.getCategories().stream().map(c -> c.getName()).toList())
    	        					: "";
    	        	String publishers = book.getPublishers() != null
    	        			? String.join(", ", book.getPublishers().stream().map(c -> c.getName()).toList())
    	        					: "";

    	        	model.addRow(new Object[]{
    	        			book.getId(),
    	        			book.getName(),
    	        			authors,
    	        			categories,
    	        			publishers,
    	        			book.getQuantity()
    	        	});
    	        }
    	 }catch(Exception e) {
    		 if(tx != null) tx.rollback();
    		 e.printStackTrace();
    	 }finally {
    		 if(session != null) session.close();
    	 }
	}
}
