package com.skilldistillery.quizme;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {
	@RequestMapping("/takeQuiz/{quizName}")
	public Quiz getQuiz(@PathVariable("quizName") String quizName){
		
	
		return null;
	}
	
	
}
