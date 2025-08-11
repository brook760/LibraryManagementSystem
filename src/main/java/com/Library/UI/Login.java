package com.Library.UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.Library.Entity.User;
import com.Library.Util.HibernateUtil;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener 
{
	  private JTextField usernameField;
	  private JPasswordField passwordField;
	  private JButton loginButton, clearButton;

	  public Login() 
	  {
	    setTitle("Library Login Page");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setSize(1280, 720); //HD size
	    setLocationRelativeTo(null); // center the window
	 // Gradient background panel
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(72, 207, 173);
                Color color2 = new Color(126, 122, 234);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
       gradientPanel.setLayout(new GridBagLayout()); // center the card panel

       // Card panel (semi-transparent)
       JPanel cardPanel = new JPanel();
       cardPanel.setBackground(new Color(255, 255, 255, 70));
       cardPanel.setPreferredSize(new Dimension(450, 350));
       cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
       cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
       
       // Title
       JLabel titleLabel = new JLabel("SIGN IN", SwingConstants.CENTER);
       titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
       titleLabel.setForeground(Color.BLACK);
       titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
       
       // User name
       usernameField = new JTextField();
       usernameField.setFont(new Font("SansSerif", Font.PLAIN, 20));
       usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
       usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

       // Password
       passwordField = new JPasswordField();
       passwordField.setFont(new Font("SansSerif", Font.PLAIN, 20));
       passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
       passwordField.setBorder(BorderFactory.createTitledBorder("Password"));


       // Buttons
       loginButton = new JButton("LOGIN");
       loginButton.setFont(new Font("SansSerif", Font.BOLD, 20));
       loginButton.setBackground(new Color(150, 120, 255));
       loginButton.setForeground(Color.WHITE);
       loginButton.setFocusPainted(false);
       loginButton.addActionListener(this);

       clearButton = new JButton("CLEAR");
       clearButton.setFont(new Font("SansSerif", Font.BOLD, 20));
       clearButton.setBackground(Color.LIGHT_GRAY);
       clearButton.setFocusPainted(false);
       clearButton.addActionListener(e -> {
           usernameField.setText("");
           passwordField.setText("");
       });

       JPanel buttonPanel = new JPanel();
       buttonPanel.setOpaque(false);
       buttonPanel.add(loginButton);
       buttonPanel.add(clearButton);

       // Add components
       cardPanel.add(titleLabel);
       cardPanel.add(Box.createVerticalStrut(20));
       cardPanel.add(usernameField);
       cardPanel.add(Box.createVerticalStrut(15));
       cardPanel.add(passwordField);
       cardPanel.add(Box.createVerticalStrut(20));
       cardPanel.add(buttonPanel);

       gradientPanel.add(cardPanel);
       setContentPane(gradientPanel);
       setVisible(true);
       
	  }

	  // Login logic
	  public void actionPerformed(ActionEvent e) 
	  {
		  String username = usernameField.getText();
	      String password = new String(passwordField.getPassword());
	      
	      try (Session session = HibernateUtil.getSessionFactory().openSession()) {
	            Query<User> query = session.createQuery("FROM User WHERE username = :u AND password = :p", User.class);
	            query.setParameter("u", username);
	            query.setParameter("p", password);

	            User user = query.uniqueResult();
	            
	            if (user != null) {
	                JOptionPane.showMessageDialog(this, "Login successful!");
	                new Dashboard().setVisible(true);
	                
	                dispose();
	            } else {
	                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Database error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
	        }
	  }

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Login::new);
		  //new Login();
	    }
	}