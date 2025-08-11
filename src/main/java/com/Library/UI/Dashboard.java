package com.Library.UI;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDesktopPane desktopPane; //Where forms/cards are shown

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
	    
        desktopPane = new JDesktopPane();
        getContentPane().add(desktopPane); // fill the frame
        
        // ----- Side bar Navigation -----
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(123, 31, 162)); // purple
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        
        JLabel title = new JLabel("Dashboard");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(25,0,30,0));
        sidebar.add(title);
        
        //Buttons
        JButton addBookBtn = new JButton("➕ Add Book");
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
        	try {
                addBook.setSelected(true);
            } catch (java.beans.PropertyVetoException ex) {
                ex.printStackTrace();
            }
        });
        sidebar.add(addBookBtn);

        add(sidebar, BorderLayout.WEST);



        // Home Panel
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new GridLayout(1, 3, 40, 40));
        homePanel.setBackground(new Color(245, 245, 255));
        homePanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        homePanel.add(createStatCard("Books", "245"));
        homePanel.add(createStatCard("Students", "125"));
        homePanel.add(createStatCard("Borrowed", "89"));
        
        
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
        	BorrowBookForm addBookWindow = new BorrowBookForm();
        	addBookWindow.setVisible(true);
        });

        sidebar.add(btnBorrowBook);
        
    }
 private void openInternalFrame(JInternalFrame frame) {
	 desktopPane.add(frame);
	    frame.setVisible(true);
	    try {
	        frame.setSelected(true);
	    } catch (java.beans.PropertyVetoException e) {
	        e.printStackTrace();
	    }
	}
	// Reusable inner feature panel with "Back to Dash board"
   /* class FeaturePanel extends JPanel {
        public FeaturePanel(String title, CardLayout cl, JPanel container) {
            setBackground(new Color(245, 245, 255));
            setLayout(new BorderLayout());

            JLabel label = new JLabel(title, JLabel.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 28));

            JButton backBtn = new JButton("← Back to Dashboard");
            backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            backBtn.setBackground(Color.LIGHT_GRAY);
            backBtn.addActionListener(e -> cl.show(container, "home"));

            JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topBar.setOpaque(false);
            topBar.add(backBtn);

            add(topBar, BorderLayout.NORTH);
            add(label, BorderLayout.CENTER);
        }
    } */
    // Helper: Side bar Button
    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBackground(new Color(145, 85, 220));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
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

 // -- Example Panels for demo only ---
    class ViewBooksPanel extends JPanel {
        public ViewBooksPanel() {
            setBackground(new Color(245, 245, 255));
            setLayout(new BorderLayout());
            JLabel label = new JLabel("View Books Table", JLabel.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 28));
            add(label, BorderLayout.CENTER);
        }
    }
    class AddStudentPanel extends JPanel {
        public AddStudentPanel() {
            setBackground(new Color(245, 245, 255));
            setLayout(new BorderLayout());
            JLabel label = new JLabel("Add Student Form", JLabel.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 28));
            add(label, BorderLayout.CENTER);
        }
    }
    class ViewStudentsPanel extends JPanel {
        public ViewStudentsPanel() {
            setBackground(new Color(245, 245, 255));
            setLayout(new BorderLayout());
            JLabel label = new JLabel("View Students Table", JLabel.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 28));
            add(label, BorderLayout.CENTER);
        }
    }
    class ManageStudentsPanel extends JPanel {
        public ManageStudentsPanel() {
            setBackground(new Color(245, 245, 255));
            setLayout(new BorderLayout());
            JLabel label = new JLabel("Manage Students Form", JLabel.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 28));
            add(label, BorderLayout.CENTER);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Dashboard().setVisible(true);
        });
    }
}
