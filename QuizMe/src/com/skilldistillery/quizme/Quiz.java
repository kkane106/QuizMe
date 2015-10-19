package com.skilldistillery.quizme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
@NamedQueries({
	@NamedQuery(name = "Quiz.getQuizzesByName", query = "Select q from Quiz q where q.quizName = :name"),

	@NamedQuery(name = "Quiz.getQuizByNameAndAuthor", query = "Select q from Quiz q where q.quizName = :name and q.author= :author"),
	
	@NamedQuery(name = "Quiz.getQuizzesByDate", query = "select q from Quiz q ORDER BY q.dateCreated")
})
public class Quiz {
	
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Question> questions;
	private String quizName, description;
	
	@Temporal(TemporalType.DATE)
	private Date dateCreated;
	private String author;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	public Quiz(List<Question> questions, String quizName, String description, Date dateCreated, String author) {
		super();
		this.questions = questions;
		this.quizName = quizName;
		this.description = description;
		this.dateCreated = dateCreated;
		this.author = author;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Quiz(ArrayList<Question> questions, String quizName, String description, Date dateCreated, String author,
			long id) {
		super();
		this.questions = questions;
		this.quizName = quizName;
		this.description = description;
		this.dateCreated = dateCreated;
		this.author = author;
		this.id = id;
	}
	
	public Quiz(){
		
	}
}
