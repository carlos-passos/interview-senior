package br.com.brainweb.interview.core.features.hero.body;

import java.util.Date;

import br.com.brainweb.interview.model.Race;
import lombok.Data;

@Data
public class HeroResponse {
	
	private String id;
	
	private String name;
	
	private Race race;
	
	private Boolean enabled;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private String powerStatsId;

}
