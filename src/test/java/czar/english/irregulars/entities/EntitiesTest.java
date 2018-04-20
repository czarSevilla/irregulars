package czar.english.irregulars.entities;

import static czar.english.irregulars.entities.PropertiesTester.verify;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class EntitiesTest {
	@Test
	public void modelTest() throws InstantiationException, IllegalAccessException, InvocationTargetException {
		assertTrue("Attempt.class fail", verify(Attempt.class));
		assertTrue("Quiz.class fail", verify(Quiz.class));
		assertTrue("Verb.class fail", verify(Verb.class));
	}
}
