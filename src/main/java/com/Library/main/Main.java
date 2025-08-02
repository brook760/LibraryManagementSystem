package com.Library.main;

import java.util.Arrays;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.Library.Entity.Author;
import com.Library.Entity.Book;
import com.Library.Entity.Category;
import com.Library.Entity.Publisher;
import com.Library.Util.HibernateUtil;

public class Main {

    public static void main(String[] args) {
    	
    	Session session = null;
    	Transaction tx =null;
    	
    	try {
    		
    		session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            
            // --- Create Author ---
            Author author = new Author();
            author.setName("George Orwell");
            
            // --- Create Category ---
            Category category = new Category();
            category.setName("Dystopian");
            
            // --- Create Publisher ---
            Publisher publisher = new Publisher();
            publisher.setName("Penguin Books");
            
            // --- Create Book ---
            Book book = new Book();
            book.setName("1984");
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
            System.out.println("✔ Book and related entities saved successfully!");
            
    	}catch(Exception e) {
    		if(tx != null)tx.rollback();
    		e.printStackTrace();
    	}finally {
    		if(session !=null) session.close();
    		HibernateUtil.shutdown();
    	}

        // Create repositories
        //AuthorRepo authorRepo = new AuthorRepo();
        //CategoryRepo categoryRepo = new CategoryRepo();
        //PublisherRepo publisherRepo = new PublisherRepo();
        //BookRepo bookRepo = new BookRepo();

        // --- Create Author ---
        //Author author = new Author();
        //author.setName("J.K. Rowling");

        // --- Create Category ---
        //Category category = new Category();
        //category.setName("Fantasy");

        // --- Create Publisher ---
        //Publisher publisher = new Publisher();
        //publisher.setName("Bloomsbury");

        // --- Create Book ---
        //Book book = new Book();
        //book.setName("Harry Potter and the Philosopher's Stone");
        //book.setAuthors(Arrays.asList(author));
        //book.setCategories(Arrays.asList(category));
        //book.setPublishers(Arrays.asList(publisher));

        // Set back-reference (important for bidirectional mapping)
        //author.setBooks(Arrays.asList(book));
        //category.setBooks(Arrays.asList(book));
        //publisher.setBooks(Arrays.asList(book));


        // --- Save Everything ---
        //bookRepo.saveBook(book);  // This will cascade save author, category, publisher
        //Session session = HibernateUtil.getSessionFactory().openSession();
        //Transaction tx = session.beginTransaction();

        //session.persist(author);
        //session.persist(category);
        //session.persist(publisher);
        //session.persist(book);

        //tx.commit();
        //session.close();
        //System.out.println("✔ Book and related entities saved successfully!");

        //HibernateUtil.shutdown(); // Always close SessionFactory
    }
}
