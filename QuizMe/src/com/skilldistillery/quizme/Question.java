package com.skilldistillery.quizme;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	Quiz parentQuiz;
	
	@OneToMany
	List<AnswerChoice> answerChoices;
	private String questionText;
	public List<AnswerChoice> getCorrectAnswer(){
		ArrayList<AnswerChoice> correctAnswers = new ArrayList<AnswerChoice>();
		for(AnswerChoice ac : answerChoices){
			if(ac.isCorrect()){
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
		this.parentQuiz = parentQuiz;
		this.answerChoices = answerChoices;
	}

	public Question(long id, Quiz parentQuiz, List<AnswerChoice> answerChoices) {
		super();
		this.id = id;
		this.parentQuiz = parentQuiz;
		this.answerChoices = answerChoices;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Quiz getParentQuiz() {
		return parentQuiz;
	}

	public void setParentQuiz(Quiz parentQuiz) {
		this.parentQuiz = parentQuiz;
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
