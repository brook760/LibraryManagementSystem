package com.Library.UI;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton addBookBtn;
    private JButton removeBookBtn;
    private JButton borrowBookBtn;
    private JButton viewBooksBtn;
    private JButton addStudentBtn;

    public Dashboard() {
        setTitle("Library Management Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JLabel titleLabel = new JLabel("Library Dashboard", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        addBookBtn = new JButton("➕ Add Book");
        removeBookBtn = new JButton("❌ Remove Book");
        borrowBookBtn = new JButton("📖 Borrow Book");
        viewBooksBtn = new JButton("📚 View Books");
        addStudentBtn = new JButton("👨‍🎓 Add Student");
        

        addBookBtn.setPreferredSize(new Dimension(200, 40));
        removeBookBtn.setPreferredSize(new Dimension(200, 40));
        borrowBookBtn.setPreferredSize(new Dimension(200, 40));
        viewBooksBtn.setPreferredSize(new Dimension(200, 40));
        addStudentBtn.setPreferredSize(new Dimension(200, 40));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        buttonPanel.add(addBookBtn);
        buttonPanel.add(removeBookBtn);
        buttonPanel.add(borrowBookBtn);
        buttonPanel.add(viewBooksBtn);
        buttonPanel.add(addStudentBtn);

        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        registerListeners();
    }

    private void registerListeners() {
        addBookBtn.addActionListener(e -> {
            AddBookForm addBookForm = new AddBookForm();
            addBookForm.setVisible(true);
        });

        removeBookBtn.addActionListener(e -> {
            RemoveBookForm removeBookForm = new RemoveBookForm();
            removeBookForm.setVisible(true);
        });

        borrowBookBtn.addActionListener(e -> {
            BorrowBookForm borrowBookForm = new BorrowBookForm();
            borrowBookForm.setVisible(true);
        });

        viewBooksBtn.addActionListener(e -> {
            ViewBooksForm viewBooksForm = new ViewBooksForm();
            viewBooksForm.setVisible(true);
        });
        
        addStudentBtn.addActionListener(e -> {
            AddStudentForm addStudentForm = new AddStudentForm();
            addStudentForm.setVisible(true);
        });
    }

    //public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> {
            //new Dashboard().setVisible(true);
        //});
    //}
}
