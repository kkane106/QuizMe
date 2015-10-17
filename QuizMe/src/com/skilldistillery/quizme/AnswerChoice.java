package com.skilldistillery.quizme;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table
public class AnswerChoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String answerText;
	private Boolean isRight;
	
	public Boolean getIsRight(){
		return isRight;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isRight() {
		return isRight;
	}

	public void setIsRight(boolean isRight) {
		this.isRight = isRight;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public String getAnswerText(){
		return answerText;
	}
	
	public AnswerChoice(){
		
	}
	
	public AnswerChoice(long id, boolean isRight, String answerText){
		this.id = id; 
		this.isRight = isRight;
		this.answerText = answerText;
	}
	
	public AnswerChoice(boolean isRight, String answerText){
		this.isRight = isRight;
		this.answerText = answerText;
	}
}
