package czar.english.irregulars.services.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import czar.english.irregulars.dtos.Result;
import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;
import czar.english.irregulars.entities.Verb;
import czar.english.irregulars.repositories.AttemptRepository;
import czar.english.irregulars.repositories.QuizRepository;
import czar.english.irregulars.repositories.VerbRepository;
import czar.english.irregulars.services.QuizService;
import czar.english.irregulars.test.TestUtils;

@RunWith(SpringRunner.class)
public class QuizServiceImplTest {

	@TestConfiguration
	static class QuizServiceImplConfig {
		
		@Bean
		public QuizService quizService() {
			QuizService quizService = new QuizServiceImpl();
			return quizService;
		}
	}

	@MockBean
	private VerbRepository verbRepository;
	
	@MockBean
	private AttemptRepository attemptRepository;
	
	@MockBean
	private QuizRepository quizRepository;
	
	@Autowired
	private QuizService quizService;
	
	@Before
	public void init() {
		List<Verb> verbs = TestUtils.buildList(5);
		Mockito.when(verbRepository.findAll()).thenReturn(verbs);
		
		Attempt dbAttempt = TestUtils.buildAttempt();
		Mockito.when(attemptRepository.findOne(1L)).thenReturn(dbAttempt);
		
		Quiz quiz = TestUtils.buildQuiz(1L, 10, 10);
		Mockito.when(quizRepository.getOne(1L)).thenReturn(quiz);
		
		Mockito.when(attemptRepository.countByQuizAndCorrect(quiz, true)).thenReturn(10);
		
	}
	
	@Test
	public void initTest() {
		Quiz quiz = new Quiz();
		for (int i = 0; i < 400; i++) {
			Attempt attempt = quizService.init(quiz);
			assertNotNull(attempt);
		}
	}
	
	@Test
	public void processAttemptTest() {
		Attempt attempt = new Attempt();
		attempt.setTopic(AttemptBuilder.INFINITIVE);
		attempt.setId(1L);
		
		quizService.processAttempt(attempt);
		
		Mockito.verify(attemptRepository, Mockito.times(1)).save(Mockito.any(Attempt.class));
		
	}
	
	@Test
	public void isLastTrueTest() {
		Attempt attempt = TestUtils.buildAttempt();
		Quiz quiz = TestUtils.buildQuiz(1L, 10, 10);
		Mockito.when(attemptRepository.countByQuiz(quiz)).thenReturn(10);
		Mockito.when(quizRepository.getOne(1L)).thenReturn(quiz);
		
		boolean r = quizService.isLast(attempt);
		
		assertTrue(r);
	}
	
	@Test
	public void isLastFalseTest() {
		Attempt attempt = TestUtils.buildAttempt();
		Quiz quiz = TestUtils.buildQuiz(1L, 10, 10);
		Mockito.when(attemptRepository.countByQuiz(quiz)).thenReturn(5);
		
		boolean r = quizService.isLast(attempt);
		
		assertFalse(r);
	}
	
	@Test
	public void getNextTest() {
		
		quizService.getNext(1L);
		
		verify(quizRepository, atLeast(1)).getOne(1L);
		verify(verbRepository, atLeast(1)).findAll();
	}
	
	@Test
	public void getResultTest() {
		
		Result r = quizService.getResult(1L);
		
		assertNotNull(r);
	}
}
