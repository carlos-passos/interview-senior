package br.com.brainweb.interview.core.features.powerstats;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.brainweb.interview.model.PowerStats;

@Repository
public interface PowerStatsRepository extends CrudRepository<PowerStats, String> {
	
	List<PowerStats> findAll();
	
	@Query("select * from power_stats where id::text = trim(from :id)")
	Optional<PowerStats> findById(String id);
	
}