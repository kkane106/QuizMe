package com.skilldistillery.quizme;

import java.util.Vector;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {
	@RequestMapping("/takeQuiz/{quizAuthor}/{quizId}")
	public Quiz getQuiz(@PathVariable("quizAuthor") String quizAuthor, @PathVariable("quizId") String idString){
		long id;
		try{
			id = Long.parseLong(idString);
		}catch(NumberFormatException nfe){
			return null;
		}
		
		QuizDAO quizDAO = new QuizDAO();
		Quiz q = quizDAO.em.find(Quiz.class, id);
		
		return q;
	}
	
	
}
