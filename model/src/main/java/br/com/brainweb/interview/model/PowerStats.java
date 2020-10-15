package br.com.brainweb.interview.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(value="power_stats")
public class PowerStats  implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private Integer strength;
	
	private Integer agility;
	
	private Integer dexterity;
	
	private Integer intelligence;
	
	@Column(value="created_at")
	private Date createdAt;
	
	@Column(value="updated_at")
	private Date updatedAt;
	
}
