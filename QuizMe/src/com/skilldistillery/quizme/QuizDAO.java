package com.skilldistillery.quizme;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;

@NamedQueries({
	@NamedQuery(name = "Quiz.getQuizzesByName", query = "Select q from Quiz q where q.quizName = :name"),

	@NamedQuery(name = "Quiz.getQuizByNameAndAuthor", query = "Select q from Quiz q where q.quizName = :name and q.author= :author")  
})
public class QuizDAO {
	public EntityManager em = Persistence.createEntityManagerFactory("QuizMe").createEntityManager();
	
	public Quiz find(long id){
		return em.find(Quiz.class, id);
	}
	
	public Quiz getQuizByName(String name){
		@SuppressWarnings("unchecked")
		ArrayList<Quiz> quizzes= (ArrayList<Quiz>) em.createNamedQuery("Quiz.findQuizByName").setParameter("quizName",name).getResultList();
		
		return quizzes.get(0); //Return first result;
	}
	
	public ArrayList<Quiz> getQuizzesByName(String name){
		@SuppressWarnings("unchecked")
		ArrayList<Quiz> quizzes= (ArrayList<Quiz>) em.createNamedQuery("Quiz.findQuizByName").setParameter("quizName",name).getResultList();
		
		return quizzes; //Return first result;
	}
	
	public boolean persistQuiz(Quiz q){
		em.getTransaction().begin();
		em.persist(q);
		em.getTransaction().commit();
		
		if(em.find(Quiz.class, q.getId()) != null){
			return true;
		}else{
			return false;
		}
	}
}
