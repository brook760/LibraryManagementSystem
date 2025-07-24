package com.anp;

import java.util.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class App 
{
	public static void main(String [] args) 
	{
		System.out.println("Welcome user");
		Configuration confi=new Configuration();
	    confi.configure();
	    SessionFactory sf=confi.buildSessionFactory();
	     
	     
	     
	}
}
