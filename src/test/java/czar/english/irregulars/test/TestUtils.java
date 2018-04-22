package czar.english.irregulars.test;

import java.util.ArrayList;
import java.util.List;

import czar.english.irregulars.dtos.Result;
import czar.english.irregulars.entities.Attempt;
import czar.english.irregulars.entities.Quiz;
import czar.english.irregulars.entities.Verb;
import czar.english.irregulars.services.impl.AttemptBuilder;

public class TestUtils {

	public static List<Verb> buildList(int count) {
		List<Verb> list = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			Verb v = new Verb();
			v.setId(Long.valueOf(i + 1));
			v.setInfinitive("infinite" + i);
			v.setParticiple("participle" + i);
			v.setPast("past" + i);
			v.setSpanish("spanish" + i);
			v.setSpanishParticiple("spanishParticiple" + i);
			v.setSpanishPast("spanishPast" + i);
			list.add(v);			
		}
		return list;
	}
	
	public static Quiz buildQuiz(Long id, Integer correct, Integer level) {
		Quiz quiz = new Quiz();
		quiz.setCorrects(correct);
		quiz.setId(id);
		quiz.setLevel(level);
		return quiz;
	}
	
	public static Attempt buildAttempt() {
		Attempt attempt = new Attempt();
		attempt.setBase(AttemptBuilder.INFINITIVE);
		attempt.setBaseType(AttemptBuilder.PARTICIPLE);
		attempt.setCorrect(false);
		attempt.setId(1L);
		attempt.setOptionA("optionA");
		attempt.setOptionB("optionB");
		attempt.setOptionC("optionC");
		attempt.setOptionD("optionD");
		attempt.setOptionE("optionE");
		attempt.setQuiz(buildQuiz(1L, 3, 10));
		attempt.setSelected("optionD");
		attempt.setTopic(AttemptBuilder.PAST);
		attempt.setTopicType(AttemptBuilder.PAST);
		return attempt;
	}
	
	public static Result buildResult() {
		Result r = new Result();
		r.setCorrectAnswers(10);
		r.setTotalAnswers(10);
		return r;
	}
}
