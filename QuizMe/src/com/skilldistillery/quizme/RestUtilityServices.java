package com.skilldistillery.quizme;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ResponseBody
public class RestUtilityServices {
	
	@RequestMapping("/newQuestion")
	public String fetchNewQuestionFragment(HttpServletRequest request){
		try {
			ServletContext context = request.getServletContext();
			List<String> lines = Files.readAllLines(Paths.get(context.getRealPath("/questionFragment.html")));
			String fragment = "";
			for(String s : lines){
				fragment += s;
			}
			
			return fragment;
		} catch (IOException e) {
			e.printStackTrace();
			return "File I/O Error:";
		}
	}
	
	@RequestMapping("/answerChoice")
	@ResponseBody
	public String fetchAnswerChoiceFragment(HttpServletRequest request){
		ServletContext context = request.getServletContext();

		try {
			List<String> lines = Files.readAllLines(Paths.get(context.getRealPath("/answerChoiceFragment.html")));
			String fragment = "";
			for(String s : lines){
				fragment += s;
			}
			
			return fragment;
		} catch (IOException e) {
			e.printStackTrace();
			return "File I/O Error";
		}
	}
	
	@RequestMapping("/submitNewQuiz")
	public boolean submitQuiz(@RequestParam("quiz") String json){
		ObjectMapper mapper = new ObjectMapper();
		try {
			Quiz q = mapper.readValue(json, Quiz.class);
			
			q.setDateCreated(new Date());
			
			QuizDAO quizDAO = new QuizDAO();
			//Check to make sure Author and QuizName pair is unique
			 Vector<Quiz> quizzes = (Vector<Quiz>) quizDAO.em.createNamedQuery("Quiz.getQuizByNameAndAuthor")
			.setParameter("name", q.getQuizName())
			.setParameter("author", q.getAuthor())
			.getResultList();
			
			 
			//Persist if there are no results;	
			if(quizzes.size() <= 0){ 
				quizDAO.persistQuiz(q);
			}else{
				return false;
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@RequestMapping("/searchQuiz")
	public Vector<Quiz> search(@RequestParam("q") String query){
		QuizDAO quizDAO = new QuizDAO();
		return (Vector<Quiz>) quizDAO.em.createNamedQuery("Quiz.getQuizzesByName").setParameter("name", query).getResultList();
	}
	
	@RequestMapping("/recentQuizzes")
	public Vector<Quiz> getRecentQuizzes(){
		QuizDAO quizDAO = new QuizDAO();
		Vector<Quiz> recentQuiz = (Vector<Quiz>) quizDAO.em.createNamedQuery("Quiz.getQuizzesByDate").setMaxResults(10).getResultList();
		return recentQuiz;
	}
	
	@RequestMapping("/quizPanel")
	@ResponseBody
	public String getQuizPanel(HttpServletRequest request){
		ServletContext context = request.getServletContext();

		try {
			ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get(context.getRealPath("/quiz.html")));
			String line = "";
			for(String s: lines){
				line += s;
			}
			
			return line;
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}

	}
}




