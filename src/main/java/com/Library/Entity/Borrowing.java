package com.Library.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Borrowing")
public class Borrowing {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long borrowingId;

	    @ManyToOne
	    @JoinColumn(name = "book_id", nullable = false)
	    private Book book;
	   
	    @ManyToOne
	    @JoinColumn(name = "student_id", nullable = false)
	    private Student student;

	    @Column(name = "quantity", nullable = false)
	    private int quantity;

		public Borrowing() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Borrowing(Long borrowingId, Book book, Student student, int quantity) {
			super();
			this.borrowingId = borrowingId;
			this.book = book;
			this.student = student;
			this.quantity = quantity;
		}

		public Long getBorrowingId() {
			return borrowingId;
		}

		public void setBorrowingId(Long borrowingId) {
			this.borrowingId = borrowingId;
		}

		public Book getBook() {
			return book;
		}

		public void setBook(Book book) {
			this.book = book;
		}

		public Student getStudent() {
			return student;
		}

		public void setStudent(Student student) {
			this.student = student;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

	    
}
