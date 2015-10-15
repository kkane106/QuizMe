package com.skilldistillery.quizme;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizController {
	@RequestMapping("/getQuiz")
	public Quiz getQuiz(@RequestParam("quizName") String quizName){
		
	
		return null;
	}
	
	
}
