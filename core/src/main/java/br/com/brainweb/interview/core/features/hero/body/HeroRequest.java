package br.com.brainweb.interview.core.features.hero.body;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.brainweb.interview.model.Race;
import lombok.Data;

@Data
public class HeroRequest {
	
	@NotBlank(message="{name.not.blank}")
	private String name;
	
	@NotNull(message="{race.not.blank}")
	private Race race;
	
	@NotNull(message="{enabled.not.blank}")
	private Boolean enabled;
	
	@NotBlank(message="{power.stats.id.not.blank}")
	private String powerStatsId;


}
