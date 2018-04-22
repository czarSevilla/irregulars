package czar.english.irregulars.controllers;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;
import czar.english.irregulars.services.QuizService;
import czar.english.irregulars.test.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class QuizControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private QuizService quizService;

	@Test
	public void initTest() throws Exception {
		
		mvc.perform(get("/quiz/init"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void postInitTest() throws Exception {
		Attempt attempt = TestUtils.buildAttempt();
		
		given(quizService.init(any(Quiz.class))).willReturn(attempt);
		
		mvc.perform(post("/quiz/init"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("attempt"))
			.andExpect(view().name("quiz/attempt"));
		
		then(quizService).should().init(any(Quiz.class));
	}
	
	@Test
	public void postAttemptTest() throws Exception {
		Attempt attempt = TestUtils.buildAttempt();
		
		given(quizService.processAttempt(any(Attempt.class))).willReturn(attempt);
		
		mvc.perform(post("/quiz/attempt"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("attempt"))
			.andExpect(view().name("quiz/answer"));
		
		then(quizService).should().processAttempt(any(Attempt.class));
	}
	
	@Test
	public void nextAttemptTest() throws Exception {
		
		given(quizService.isLast(any(Attempt.class))).willReturn(false);
		given(quizService.getNext(anyLong())).willReturn(TestUtils.buildAttempt());
		
		mvc.perform(post("/quiz/next").param("quiz.id", "1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("attempt"))
			.andExpect(view().name("quiz/attempt"));
		
		then(quizService).should().isLast(any(Attempt.class));
		then(quizService).should().getNext(anyLong());
		
	}
	
	@Test
	public void nextAttemptLastTest() throws Exception {
		
		given(quizService.isLast(any(Attempt.class))).willReturn(true);
		given(quizService.getResult(anyLong())).willReturn(TestUtils.buildResult());
		
		mvc.perform(post("/quiz/next").param("quiz.id", "1"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("result"))
			.andExpect(view().name("quiz/finish"));
		
		then(quizService).should().isLast(any(Attempt.class));
		then(quizService).should().getResult(anyLong());
		
	}
}
