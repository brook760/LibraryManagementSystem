package com.Library.UI;

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.Library.Entity.User;
import com.Library.Util.HibernateUtil;

@SuppressWarnings("serial")
public class Login extends JFrame implements ActionListener 
{
	   // GUI Components
	  private JTextField usernameField;
	  private JPasswordField passwordField;
	  private JButton loginButton, clearButton;

	  public Login() 
	  {
	    setTitle("Library Login Page");
	    setSize(400, 250);
	    setLocationRelativeTo(null); // center the window
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	  // Panel
	  JPanel panel = new JPanel();   
	  //panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
	  panel.setLayout(new GridLayout(4, 2, 10, 10));

	  // User name   
	  panel.add(new JLabel("Username:"));     
	  usernameField = new JTextField();
	  panel.add(usernameField);

	        
	  // Password  
	  panel.add(new JLabel("Password:"));     
	  passwordField = new JPasswordField();  
	  panel.add(passwordField);
	       
	  // Buttons
	  loginButton = new JButton("Login");
	  loginButton.addActionListener(this);   
	  panel.add(loginButton);

	  clearButton = new JButton("Clear"); 
	  clearButton.addActionListener(e -> {
		  usernameField.setText("");
		  passwordField.setText("");      
	  });
	  panel.add(loginButton);
	  panel.add(clearButton);

	  add(panel);    
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
		  new Login();
	    }
	}