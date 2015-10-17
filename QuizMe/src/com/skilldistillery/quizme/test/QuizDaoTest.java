package com.skilldistillery.quizme.test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import com.skilldistillery.quizme.QuizDAO;

public class QuizDaoTest {
	private static QuizDAO quizDAO;
	@BeforeClass
	public static void setupTest(){
		quizDAO = new QuizDAO();
	}
	
	@Test
	public void testFind() {
		quizDAO.em.createNamedQuery("findQuizByName").setParameter("name", "Test").getResultList();
	}

	@Test
	public void testGetQuizByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuizzesByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testPersistQuiz() {
		fail("Not yet implemented");
	}

}
