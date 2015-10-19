package com.skilldistillery.quizme;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

@Entity
@Table
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	

	
	
	@OneToMany(cascade=CascadeType.PERSIST)
	List<AnswerChoice> answerChoices;
	private String questionText;
	public List<AnswerChoice> getCorrectAnswer(){
		ArrayList<AnswerChoice> correctAnswers = new ArrayList<AnswerChoice>();
		for(AnswerChoice ac : answerChoices){
			if(ac.isRight()){
				correctAnswers.add(ac);
			}
		}
		return correctAnswers;
	}

	public Question() {
		super();
	}

	public Question(Quiz parentQuiz, List<AnswerChoice> answerChoices) {
		super();
		this.answerChoices = answerChoices;
	}

	public Question(long id, Quiz parentQuiz, List<AnswerChoice> answerChoices) {
		super();
		this.id = id;
		this.answerChoices = answerChoices;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<AnswerChoice> getAnswerChoices() {
		return answerChoices;
	}

	public void setAnswerChoices(List<AnswerChoice> answerChoices) {
		this.answerChoices = answerChoices;
	}
	
	public String getQuestionText(){
		return this.questionText;
	}
	
	public void setQuestionText(String questionText){
		this.questionText = questionText;
	}
}
