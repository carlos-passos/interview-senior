package br.com.brainweb.interview.core.features.crosspowers.body;

import br.com.brainweb.interview.core.features.powerstats.body.PowerStatsResponse;
import br.com.brainweb.interview.model.Race;
import lombok.Data;

@Data
public class HeroStatsResponse {
	
	private String name;
	
	private Race race;
	
	private PowerStatsResponse powerStats;

}
