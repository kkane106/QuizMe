package com.skilldistillery.quizme;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {
	@RequestMapping("/takeQuiz/{quizAuthor}/{quizName}")
	public Quiz getQuiz(@PathVariable("quizAuthor") String quizAuthor, @PathVariable("quizName") String quizName){
		QuizDAO quizDAO = new QuizDAO();
		Quiz q = (Quiz) quizDAO.em.createNamedQuery("Quiz.getQuizByNameAndAuthor")
		.setParameter("quizAuthor", quizAuthor)
		.setParameter("quizName", quizName).getResultList().get(0);
	
		return q;
	}
	
	
}
