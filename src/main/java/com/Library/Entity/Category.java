package com.Library.Entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable= false)
	private String name;
	
	@ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL )
	private List<Book> books;

	public Category() {
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

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> list) {
		this.books = list;
	}
}
