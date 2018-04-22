package czar.english.irregulars.services.impl;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
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

import czar.english.irregulars.entities.Verb;
import czar.english.irregulars.repositories.VerbRepository;
import czar.english.irregulars.services.VerbService;

@RunWith(SpringRunner.class)
public class VerbServiceImplTest {
	
	@TestConfiguration
	static class VerbServiceImplConfig {
		
		@Bean
		public VerbService verbService() {
			VerbService verbService = new VerbServiceImpl();
			return verbService;
		}
	}

	@MockBean
	private VerbRepository verbRepository;
	
	@Autowired
	private VerbService verbService;
	
	@Before
	public void init() {
		List<Verb> value = new ArrayList<>();
		Mockito.when(verbRepository.findAll()).thenReturn(value);
	}
	
	@Test
	public void findAllTest() {
		
		List<Verb> value = verbService.findAll();
		
		Mockito.verify(verbRepository, Mockito.times(1)).findAll();
		assertNotNull("List<Verb> value is null", value);
	}
}
