package czar.english.irregulars.dtos;

import static czar.english.irregulars.entities.PropertiesTester.verify;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class DtosTest {

	@Test
	public void dtosTest() throws InstantiationException, IllegalAccessException, InvocationTargetException {
		assertTrue("Attempt.class fail", verify(Result.class));
	}
}
