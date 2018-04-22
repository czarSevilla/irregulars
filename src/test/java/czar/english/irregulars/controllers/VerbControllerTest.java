package czar.english.irregulars.controllers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import czar.english.irregulars.services.VerbService;
import czar.english.irregulars.test.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class VerbControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private VerbService verbService;	

	@Test
	public void mainTest() throws Exception {
		given(verbService.findAll())
			.willReturn(TestUtils.buildList(5));
		
		mvc.perform(get("/verbs/list"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("Quiz Irregular Verbs")));
	}
}
