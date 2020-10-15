package br.com.brainweb.interview.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(value="hero")
public class Hero implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String name;
	
	private Race race;
	
	private Boolean enabled;
	
	@Column(value="created_at")
	private Date createdAt;
	
	@Column(value="updated_at")
	private Date updatedAt;
	
	@Column(value="power_stats_id")
	private String powerStatsId;
		
}
