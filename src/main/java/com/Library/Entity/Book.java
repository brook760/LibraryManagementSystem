package com.Library.Entity;

import java.util.Set;

import jakarta.persistence.*;


@Entity
@Table(name = "book")
public class Book {

	@Id
	@Column(name="book_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "book_authors",
				joinColumns = {@JoinColumn(name="book_id")},
				inverseJoinColumns = {@JoinColumn(name= "author_id")})
	private Set<Author> authors;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "book_category",
				joinColumns = {@JoinColumn(name="book_id")},
				inverseJoinColumns = {@JoinColumn(name= "category_id")})
	private Set<Category> categories;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "book_publishers",
				joinColumns = {@JoinColumn(name="book_id")},
				inverseJoinColumns = {@JoinColumn(name= "publisher_id")})
	private Set<Publisher> publishers;
	
	private int Quantity;
	
	public Book() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Publisher> getPublishers() {
		return publishers;
	}

	public void setPublishers(Set<Publisher> publisher) {
		this.publishers = publisher;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
	
}
