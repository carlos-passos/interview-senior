package br.com.brainweb.interview.core.features.powerstats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.brainweb.interview.model.PowerStats;

@Service
public class PowerStatsService {
	
	@Autowired
	private PowerStatsRepository powerStatsRepository;

	public List<PowerStats> getPowerStats() {
		List<PowerStats> stats = powerStatsRepository.findAll();
		if(stats == null || stats.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, 
					"Power stats not found");
		}
		return stats;
	}
}
