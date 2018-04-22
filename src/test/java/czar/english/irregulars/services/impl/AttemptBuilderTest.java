package czar.english.irregulars.services.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Verb;
import czar.english.irregulars.test.TestUtils;

public class AttemptBuilderTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void builWithoutVerbsTest() {
		AttemptBuilder.build(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildWithoutMinimumTest() {
		AttemptBuilder.build(TestUtils.buildList(1));
	}
	
	@Test
	public void builHappyPath() {
		List<Verb> verbs = TestUtils.buildList(5);
		Attempt attempt = AttemptBuilder.build(verbs);
		assertNotNull(attempt);
	}
	
	@Test
	public void buildBaseZeroTest() {
		
		Attempt attempt = TestUtils.buildAttempt();
		
		AttemptBuilder.buildBase(attempt, null, 0);
		
		assertThat(attempt.getBaseType(), equalTo(AttemptBuilder.PARTICIPLE));
	}
	
	@Test
	public void builTopicsTest() {
		Attempt attempt = TestUtils.buildAttempt();
		List<Verb> verbs = TestUtils.buildList(10);
		Random random = new Random();
		Set<Verb> setVerbs = new HashSet<>(verbs);
		
		AttemptBuilder.buildTopics(attempt, setVerbs, verbs.get(0), 0, random);
	}
	
	@Test
	public void privateConstructorTest() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<AttemptBuilder> constructor = AttemptBuilder.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}
	
	@Test
	public void invalidBuildSpanisOptionTest() {
		Verb verb = TestUtils.buildList(1).get(0);
		String r = AttemptBuilder.buildOption("", verb, 4);
		assertNull(r);
	}
	
	@Test
	public void invalidBuildSpanishTopicTest() {
		Attempt attempt = TestUtils.buildAttempt();
		List<Verb> someVerbs = TestUtils.buildList(10);
		Set<Verb> setVerbs = new HashSet<>(someVerbs);
		Random random = new Random();
		int topic = 4;
		Verb verb = someVerbs.get(0);
		attempt.setBaseType("");
		attempt.setTopic(null);
		AttemptBuilder.buildTopics(attempt, setVerbs, verb, topic, random);
		
		assertNull(attempt.getTopic());
	}
}
