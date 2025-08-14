package com.Library.UI;

import javax.swing.*;

import org.hibernate.Session;

import com.Library.Util.HibernateUtil;

import java.awt.*;

public class Dashboard extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane; //Where forms/cards are shown
	private JInternalFrame homeFrame;

    public Dashboard() {
    	
        setTitle("Library Management Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(1280, 720); //HD size
	    setLocationRelativeTo(null); // center the window
        initUI();
    }

    private void initUI() {
    	
    	// ----- Main Layout -----
        setLayout(new BorderLayout());
        
        homeFrame = new JInternalFrame("Dashboard Overview", true, true, true, false);
        //homeFrame.setVisible(true);
	    
        desktopPane = new JDesktopPane();
        getContentPane().add(desktopPane); // fill the frame
        
        // ----- Side bar Navigation -----
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(123, 31, 162)); // purple
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        
        String booksCount = getCountFromEntity("Book");
        String studentsCount = getCountFromEntity("Student");
        // For borrowed count, if you have an entity tracking borrow records, replace "Borrow" below
        String borrowedCount = getTotalBorrowedQuantity();
        
        // Home Panel setup
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(1, 3, 40, 40));
        homePanel.setBackground(new Color(245, 245, 255));
        homePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        homePanel.add(createStatCard("Books", booksCount));
        homePanel.add(createStatCard("Students", studentsCount));
        homePanel.add(createStatCard("Borrowed", borrowedCount));

        
        JButton title = new JButton("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        title.setBackground(new Color(145, 85, 220));
        title.setForeground(Color.WHITE);
        title.setFocusPainted(false);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        
        sidebar.add(title);
        
        title.addActionListener(e -> {
        	desktopPane.add(homeFrame, JDesktopPane.DEFAULT_LAYER);

        	try {
        		homeFrame.setMaximum(true);
                homeFrame.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        });

        add(sidebar, BorderLayout.WEST);
 
        //Add Book Button
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        addBookBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addBookBtn.setBackground(new Color(145, 85, 220));
        addBookBtn.setForeground(Color.WHITE);
        addBookBtn.setFocusPainted(false);
        addBookBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        addBookBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        addBookBtn.addActionListener(e -> {
        	AddBookForm addBook= new AddBookForm();
        	
        	desktopPane.add(addBook);
        	addBook.setVisible(true);
        	try {
                addBook.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        	
        });
        
        sidebar.add(addBookBtn);
        
        //Borrow Book Button
        JButton btnBorrowBook = new JButton("Borrow Book");
        btnBorrowBook.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnBorrowBook.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnBorrowBook.setBackground(new Color(145, 85, 220));
        btnBorrowBook.setForeground(Color.WHITE);
        btnBorrowBook.setFocusPainted(false);
        btnBorrowBook.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnBorrowBook.setAlignmentX(Component.CENTER_ALIGNMENT);

        // When clicked, open AddBookFrame
        btnBorrowBook.addActionListener(e -> {
        	BorrowBookForm BorrowBook = new BorrowBookForm();
        	
        	desktopPane.add(BorrowBook);
        	try {
        		BorrowBook.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        	
        });
        
        sidebar.add(btnBorrowBook);
        
        //View Book Button
        JButton viewBooksForm = new JButton("View Book");
        viewBooksForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        viewBooksForm.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        viewBooksForm.setBackground(new Color(145, 85, 220));
        viewBooksForm.setForeground(Color.WHITE);
        viewBooksForm.setFocusPainted(false);
        viewBooksForm.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        viewBooksForm.setAlignmentX(Component.CENTER_ALIGNMENT);


        // When clicked, open AddBookFrame
        viewBooksForm.addActionListener(e -> {
        	ViewBooksForm viewBooks = new ViewBooksForm();
        	
        	desktopPane.add(viewBooks);
        	try {
        		viewBooks.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        	
        });

        sidebar.add(viewBooksForm);

        //View Book Button
        JButton removeBookFrom = new JButton("View Book");
        removeBookFrom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        removeBookFrom.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        removeBookFrom.setBackground(new Color(145, 85, 220));
        removeBookFrom.setForeground(Color.WHITE);
        removeBookFrom.setFocusPainted(false);
        removeBookFrom.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        removeBookFrom.setAlignmentX(Component.CENTER_ALIGNMENT);


        // When clicked, open AddBookFrame
        removeBookFrom.addActionListener(e -> {
        	RemoveBookForm removeBooks = new RemoveBookForm();
        	
        	desktopPane.add(removeBooks);
        	try {
        		removeBooks.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        	
        });

        sidebar.add(removeBookFrom);
        
        //Add Student Button
        JButton addStudentForm = new JButton("Add Student");
        addStudentForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        addStudentForm.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        addStudentForm.setBackground(new Color(145, 85, 220));
        addStudentForm.setForeground(Color.WHITE);
        addStudentForm.setFocusPainted(false);
        addStudentForm.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        addStudentForm.setAlignmentX(Component.CENTER_ALIGNMENT);


        // When clicked, open AddBookFrame
        addStudentForm.addActionListener(e -> {
        	AddStudentForm AddStudent = new AddStudentForm();
        	
        	desktopPane.add(AddStudent);
        	AddStudent.setVisible(true);
        	try {
        		AddStudent.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        	
        });

        sidebar.add(addStudentForm);
        
        //Manage Student Button
        JButton manageStudentForm = new JButton("Add Student");
        manageStudentForm.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        manageStudentForm.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        manageStudentForm.setBackground(new Color(145, 85, 220));
        manageStudentForm.setForeground(Color.WHITE);
        manageStudentForm.setFocusPainted(false);
        manageStudentForm.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        manageStudentForm.setAlignmentX(Component.CENTER_ALIGNMENT);


        // When clicked, open AddBookFrame
        manageStudentForm.addActionListener(e -> {
        	ManageStudentsFrom editStudent = new ManageStudentsFrom();
        	desktopPane.add(editStudent);
        	try {
        		editStudent.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }

        });

        sidebar.add(manageStudentForm);
        
    }
    
 
    // Helper: Stat Card (for Dash board home)
    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,225), 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 36));
        val.setForeground(new Color(123, 31, 162));
        val.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(label);
        card.add(Box.createVerticalStrut(15));
        card.add(val);
        return card;
    }
    private String getCountFromEntity(String entityName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            @SuppressWarnings("deprecation")
			Long count = (Long) session.createQuery("SELECT COUNT(*) FROM " + entityName).uniqueResult();
            return count.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }
    }

    private String getTotalBorrowedQuantity() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Long totalBorrowed = (Long) session.createQuery("SELECT SUM(b.quantity) FROM Borrowing b").uniqueResult();
            if (totalBorrowed == null) return "0";
            return totalBorrowed.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "N/A";
        }
    }
          
}
