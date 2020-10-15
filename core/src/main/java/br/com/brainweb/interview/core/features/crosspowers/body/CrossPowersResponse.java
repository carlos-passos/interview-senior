package br.com.brainweb.interview.core.features.crosspowers.body;

import lombok.Data;

@Data
public class CrossPowersResponse {
	
	private HeroStatsResponse heroOne;
	
	private HeroStatsResponse heroTwo;
	
	private String comparativeStrength;
	
	private String comparativeAgility;
	
	private String comparativeDexterity;
	
	private String comparativeIntelligence;

}
