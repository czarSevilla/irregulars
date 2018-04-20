package czar.english.irregulars.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Quiz implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	@Column
	private Integer level;
	
	@Column
	private Integer corrects;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getCorrects() {
		return corrects;
	}

	public void setCorrects(Integer corrects) {
		this.corrects = corrects;
	}
}
