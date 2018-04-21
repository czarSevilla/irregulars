package czar.english.irregulars.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import czar.english.irregulars.entities.Verb;
import czar.english.irregulars.repositories.VerbRepository;
import czar.english.irregulars.services.VerbService;

@Service("verbService")
public class VerbServiceImpl implements VerbService {
	
	@Autowired
	private VerbRepository verbRepository;

	@Override
	public List<Verb> findAll() {
		return verbRepository.findAll();
	}
	
	
}
