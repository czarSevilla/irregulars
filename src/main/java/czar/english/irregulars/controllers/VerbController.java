package czar.english.irregulars.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;
import czar.english.irregulars.entities.Verb;
import czar.english.irregulars.repositories.AttemptRepository;
import czar.english.irregulars.repositories.QuizRepository;
import czar.english.irregulars.repositories.VerbRepository;

@Controller
public class VerbController {

	private static final String INFINITIVE = "infinitive";
	private static final String PAST = "past";
	private static final String PARTICIPLE = "participle";
	private static final String SPANISH = "spanish";
	
	@Autowired
	VerbRepository verbRepository;

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	AttemptRepository attemptRepository;
	
	@RequestMapping(value = "/")
	public String main(Model model) {
		List<Verb> verbs = verbRepository.findAll();
		model.addAttribute("verbs", verbs);
		return "index";
	}

	@RequestMapping(value = "/vebs/list", method = GET)
	public String list(Model model) {
		List<Verb> verbs = verbRepository.findAll();
		model.addAttribute("verbs", verbs);
		return "verbs/list";
	}

	@RequestMapping(value = "/verbs/init", method = GET)
	public String init(Model model) {
		model.addAttribute("quiz", new Quiz());
		return "verbs/init";
	}

	@RequestMapping(value = "/verbs/init", method = POST)
	public String postInit(Model model, Quiz quiz) {

		quizRepository.save(quiz);
		
		Attempt attempt = getAttempt();
		attempt.setQuiz(quiz);
		attemptRepository.save(attempt);
		
		model.addAttribute("attempt", attempt);

		return "verbs/attempt";
	}
	
	@RequestMapping(value = "/verbs/attempt", method = POST)
	public String postAttempt(Model model, Attempt attempt) {
		Attempt dbAttempt = attemptRepository.findOne(attempt.getId());
		dbAttempt.setSelected(attempt.getSelected());
		dbAttempt.setCorrect(dbAttempt.getTopic().equals(attempt.getSelected()));
		attemptRepository.save(dbAttempt);
		model.addAttribute("attempt", dbAttempt);
		return "verbs/answer";
	}
	
	@RequestMapping(value = "/verbs/next", method = POST)
	public String nextAttempt(Model model, Attempt attempt) {
		
		Quiz quiz = quizRepository.getOne(attempt.getQuiz().getId());
		
		int currentAttempts = attemptRepository.countByQuiz(quiz);
		
		if (currentAttempts < quiz.getLevel()) {
			attempt = getAttempt();
			attempt.setQuiz(quiz);
			attemptRepository.save(attempt);
			
			model.addAttribute("attempt", attempt);
			
			return "verbs/attempt";
		} else {
			model.addAttribute("total", quiz.getLevel());
			int corrects = attemptRepository.countByQuizAndCorrect(quiz, true);
			model.addAttribute("corrects", corrects);
			return "verbs/finish";
		}		
	}

	private Attempt getAttempt() {
		Attempt attempt = new Attempt();

		List<Verb> verbs = verbRepository.findAll();

		Random random = new Random(new Date().getTime());

		int verbIdx = random.nextInt(verbs.size());

		int base = random.nextInt(4) + 1;

		int topic = 0;

		do {
			topic = random.nextInt(4) + 1;
		} while (topic == base);

		Set<Verb> someVerbs = randomVerbs(verbs, verbIdx, random);
		
		buildBase(attempt, verbs.get(verbIdx), base);
		
		buildTopics(attempt, someVerbs, verbs.get(verbIdx), topic, random);

		return attempt;
	}
	
	
	private Set<Verb> randomVerbs(List<Verb> verbs, int verbIdx, Random random) {
		Set<Verb> someVerbs = new HashSet<>();
		
		do {
			int idx = random.nextInt(verbs.size());
			if (idx != verbIdx) {
				someVerbs.add(verbs.get(idx));
			}
		} while (someVerbs.size() < 4);
		
		return someVerbs;
	}
	
	private void buildBase(Attempt attempt, Verb verb, int base) {
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
			attempt.setBaseType(SPANISH);
			attempt.setBase(verb.getSpanish());
			break;
		}
	}
	
	private void buildTopics(Attempt attempt, Set<Verb> someVerbs, Verb verb, int topic, Random random) {
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
			attempt.setTopicType(SPANISH);
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
	
	private String buildOption(Verb verb, int topic) {
		switch (topic) {
		case 1: return verb.getInfinitive();
		case 2: return verb.getPast();
		case 3: return verb.getParticiple();
		case 4: return verb.getSpanish();
		}
		return null;
	}

}
