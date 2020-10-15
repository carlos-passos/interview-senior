package br.com.brainweb.interview.core.features.hero;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.brainweb.interview.model.Hero;

@Repository
public interface HeroRepository extends CrudRepository<Hero, String> {
	
	List<Hero> findAll();
	
	@Query("select * from hero where id::text = trim(from :id)")
	Optional<Hero> findById(String id);	

	@Query("select * from hero where lower(name) = lower(trim(from :name))")
	Optional<Hero> findByName(String name);
	
	@Query("delete from hero where id::text = trim(from :id)")
	void delete(String id);
	
	@Query("insert into hero(id, name, race, power_stats_id) "
			+ "values(:id::uuid, :name, :race, :powerStatsId::uuid)")
	void save(String id, String name, String race, String powerStatsId);

	@Query("update hero set name = :name, race = :race, enabled = :enabled, "
			+ "power_stats_id = :powerStatsId::uuid, updated_at = :updatedAt where id::text = trim(from :id)")
	Hero update(String id, String name, String race, Boolean enabled, String powerStatsId, Date updatedAt);

}
