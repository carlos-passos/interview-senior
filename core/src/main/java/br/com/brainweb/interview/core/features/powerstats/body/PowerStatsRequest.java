package br.com.brainweb.interview.core.features.powerstats.body;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PowerStatsRequest {

	@NotNull(message="{strength.not.blank}")
	private Integer strength;
	
	@NotNull(message="{agility.not.blank}")
	private Integer agility;
	
	@NotNull(message="{dexterity.not.blank}")
	private Integer dexterity;
	
	@NotNull(message="{intelligence.not.blank}")
	private Integer intelligence;
	
}
