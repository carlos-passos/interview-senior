package br.com.brainweb.interview.core.features.powerstats.body;

import java.util.Date;

import lombok.Data;

@Data
public class PowerStatsResponse {
	
	private String id;

	private Integer strength;
	
	private Integer agility;

	private Integer dexterity;
	
	private Integer intelligence;
	
	private Date createdAt;
	
	private Date updatedAt;

}
