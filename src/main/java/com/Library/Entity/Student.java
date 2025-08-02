package com.Library.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

	@Id
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String rollNo;
	
	@Column
	private String email;

	public Student() {
		super();
	}

	public Student(int id, String username, String rollNo, String email) {
		super();
		this.id = id;
		this.name = username;
		this.rollNo = rollNo;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
