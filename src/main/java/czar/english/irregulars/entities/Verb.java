package czar.english.irregulars.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Verb implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column
	private Long id;

	@Column
	private String infinitive;

	@Column
	private String past;

	@Column
	private String participle;

	@Column
	private String spanish;

	@Column
	private String spanishPast;

	@Column
	private String spanishParticiple;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInfinitive() {
		return infinitive;
	}

	public void setInfinitive(String infinitive) {
		this.infinitive = infinitive;
	}

	public String getPast() {
		return past;
	}

	public void setPast(String past) {
		this.past = past;
	}

	public String getParticiple() {
		return participle;
	}

	public void setParticiple(String participle) {
		this.participle = participle;
	}

	public String getSpanish() {
		return spanish;
	}

	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}

	public String getSpanishPast() {
		return spanishPast;
	}

	public void setSpanishPast(String spanishPast) {
		this.spanishPast = spanishPast;
	}

	public String getSpanishParticiple() {
		return spanishParticiple;
	}

	public void setSpanishParticiple(String spanishParticiple) {
		this.spanishParticiple = spanishParticiple;
	}
}
