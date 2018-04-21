package czar.english.irregulars.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Verb;

public class AttemptBuilder {
	
	private static final String INFINITIVE = "infinitive";
	private static final String PAST = "past";
	private static final String PARTICIPLE = "participle";
	private static final String TRASLATION = "traslation";

	public static Attempt build(List<Verb> verbs) {
		Attempt attempt = new Attempt();

		Random random = new Random(new Date().getTime());

		int verbIdx = random.nextInt(verbs.size());

		int base = random.nextInt(4) + 1;

		int topic = 0;

		do {
			topic = random.nextInt(4) + 1;
		} while (topic == base);

		
		Set<Verb> someVerbs = new HashSet<>();

		do {
			int idx = random.nextInt(verbs.size());
			if (idx != verbIdx) {
				someVerbs.add(verbs.get(idx));
			}
		} while (someVerbs.size() < 4);


		buildBase(attempt, verbs.get(verbIdx), base);

		buildTopics(attempt, someVerbs, verbs.get(verbIdx), topic, random);

		return attempt;
	}
	
	public static void buildBase(Attempt attempt, Verb verb, int base) {
		switch (base) {
		case 1:
			attempt.setBaseType(INFINITIVE);
			attempt.setBase(verb.getInfinitive());
			break;
		case 2:
			attempt.setBaseType(PAST);
			attempt.setBase(verb.getPast());
			break;
		case 3:
			attempt.setBaseType(PARTICIPLE);
			attempt.setBase(verb.getParticiple());
			break;
		case 4:
			attempt.setBaseType(TRASLATION);
			attempt.setBase(verb.getSpanish());
			break;
		}
	}

	public static void buildTopics(Attempt attempt, Set<Verb> someVerbs, Verb verb, int topic, Random random) {
		List<String> options = new ArrayList<>();
		for (Verb currentVerb : someVerbs) {
			options.add(buildOption(currentVerb, topic));
		}

		switch(topic) {
		case 1:
			attempt.setTopicType(INFINITIVE);
			attempt.setTopic(verb.getInfinitive());
			options.add(verb.getInfinitive());
			break;
		case 2:
			attempt.setTopicType(PAST);
			attempt.setTopic(verb.getPast());
			options.add(verb.getPast());
			break;
		case 3:
			attempt.setTopicType(PARTICIPLE);
			attempt.setTopic(verb.getParticiple());
			options.add(verb.getParticiple());
			break;
		case 4:
			attempt.setTopicType(TRASLATION);
			attempt.setTopic(verb.getSpanish());
			options.add(verb.getSpanish());
			break;
		}

		Collections.shuffle(options);
		int idx = 0;
		attempt.setOptionA(options.get(idx++));
		attempt.setOptionB(options.get(idx++));
		attempt.setOptionC(options.get(idx++));
		attempt.setOptionD(options.get(idx++));
		attempt.setOptionE(options.get(idx++));
	}

	public static String buildOption(Verb verb, int topic) {
		switch (topic) {
		case 1: return verb.getInfinitive();
		case 2: return verb.getPast();
		case 3: return verb.getParticiple();
		case 4: return verb.getSpanish();
		}
		return null;
	}
}
