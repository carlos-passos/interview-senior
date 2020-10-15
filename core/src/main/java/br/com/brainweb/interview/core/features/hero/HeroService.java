package br.com.brainweb.interview.core.features.hero;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import br.com.brainweb.interview.core.features.hero.body.HeroRequest;
import br.com.brainweb.interview.core.features.powerstats.PowerStatsRepository;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.PowerStats;

@Service
public class HeroService {
	
	@Autowired
	private HeroRepository heroRepository;
	
	@Autowired
	private PowerStatsRepository powerStatsRepository;

	@CacheEvict(value = "heroes", allEntries=true)
	public void save(final HeroRequest request) {
		if(request == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, 
					"Payload required");
		}
		getPowerStatsById(request.getPowerStatsId());
		Hero hero = new DozerBeanMapper().map(request, Hero.class);
		hero.setPowerStatsId(request.getPowerStatsId());
		heroRepository.save(hero.getId(), hero.getName(), 
				hero.getRace().name(), hero.getPowerStatsId());
	}

	@CachePut(value = "heroes")
	public void update(final HeroRequest request, final String id) {
		if(request == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, 
					"Payload required");
		}
		heroRepository.findById(id).map(
			h -> {
				h.setEnabled(request.getEnabled());
				h.setName(request.getName());
				h.setRace(request.getRace());
				h.setUpdatedAt(new Date());
				h.setPowerStatsId(getPowerStatsById(request.getPowerStatsId()).getId());
				return heroRepository.update(h.getId(), h.getName(), h.getRace().name(), 
						h.getEnabled(), h.getPowerStatsId(), h.getUpdatedAt());
			}
		).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, 
						"Hero not found"));
	}

	@CacheEvict(value = "heroes", allEntries=true)
	public void delete(final String id) {
		Hero hero = getHeroById(id);
		heroRepository.delete(hero.getId());
	}

	@Cacheable(value = "heroes")
	public List<Hero> getHeroes() {
		List<Hero> heroes = heroRepository.findAll();
		if(heroes == null || heroes.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, 
					"Heroes not found");
		}
		return heroes;
	}


	@Cacheable(value = "heroes")
	public Hero getHeroById(final String id) {
		if(id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, 
					"Id required");
		} 
		return heroRepository.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, 
						"Hero not found"));
	}


	@Cacheable(value = "heroes")
	public Hero getHeroByName(final String name) {
		if(StringUtils.isBlank(name)) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, 
					"Name required");
		} 
		return heroRepository.findByName(name).orElse(new Hero());
	}
	
	public PowerStats getPowerStatsById(final String id) {
		if(id == null) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, 
					"Id required");
		}
		return powerStatsRepository.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, 
						"Power stats not found"));
	}
}
