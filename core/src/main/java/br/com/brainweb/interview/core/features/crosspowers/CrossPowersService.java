package br.com.brainweb.interview.core.features.crosspowers;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import br.com.brainweb.interview.core.features.crosspowers.body.CrossPowersRequest;
import br.com.brainweb.interview.core.features.crosspowers.body.CrossPowersResponse;
import br.com.brainweb.interview.core.features.crosspowers.body.HeroStatsResponse;
import br.com.brainweb.interview.core.features.hero.HeroService;
import br.com.brainweb.interview.core.features.powerstats.body.PowerStatsResponse;
import br.com.brainweb.interview.model.Hero;
import br.com.brainweb.interview.model.Power;
import br.com.brainweb.interview.model.PowerStats;

@Service
public class CrossPowersService {

	@Autowired
	private HeroService heroService;

	private CrossPowersResponse response;

	private String comparativeResult;

	private Object crossDataResult;
	
	@CacheEvict(value = "heroes", allEntries=true)
	public CrossPowersResponse consolidatedData(final CrossPowersRequest request) {

		response = new CrossPowersResponse();

		Hero heroOne = heroService.getHeroById(request.getHeroOneId());
		PowerStats powerStatsHeroOne = heroService.getPowerStatsById(heroOne.getPowerStatsId());
		response.setHeroOne(new DozerBeanMapper().map(heroOne, HeroStatsResponse.class));
		response.getHeroOne().setPowerStats(new DozerBeanMapper().map(powerStatsHeroOne, PowerStatsResponse.class));

		Hero heroTwo = heroService.getHeroById(request.getHeroTwoId());
		PowerStats powerStatsHeroTwo = heroService.getPowerStatsById(heroTwo.getPowerStatsId());
		response.setHeroTwo(new DozerBeanMapper().map(heroTwo, HeroStatsResponse.class));
		response.getHeroTwo().setPowerStats(new DozerBeanMapper().map(powerStatsHeroTwo, PowerStatsResponse.class));

		
		crossData(Power.agility, response.getHeroOne(), response.getHeroTwo());
		crossData(Power.dexterity, response.getHeroOne(), response.getHeroTwo());
		crossData(Power.intelligence, response.getHeroOne(), response.getHeroTwo());
		crossData(Power.strength, response.getHeroOne(), response.getHeroTwo());

		return response;
	}

	private void crossData(Power power, HeroStatsResponse heroOne, HeroStatsResponse heroTwo) {
		switch (power) {
		case agility:
			comparePowers(Power.agility, 
					response.getHeroOne().getName(), 
					response.getHeroOne().getPowerStats().getAgility(), 
					response.getHeroTwo().getName(),
					response.getHeroTwo().getPowerStats().getAgility());
			response.setComparativeAgility(comparativeResult);
			break;

		case dexterity:
			comparePowers(Power.dexterity, 
					response.getHeroOne().getName(), 
					response.getHeroOne().getPowerStats().getDexterity(), 
					response.getHeroTwo().getName(),
					response.getHeroTwo().getPowerStats().getDexterity());
			response.setComparativeDexterity(comparativeResult);
			break;
			
		case intelligence:
			comparePowers(Power.intelligence, 
					response.getHeroOne().getName(), 
					response.getHeroOne().getPowerStats().getIntelligence(), 
					response.getHeroTwo().getName(),
					response.getHeroTwo().getPowerStats().getIntelligence());
			response.setComparativeIntelligence(comparativeResult);
			break;
			
		case strength:
			comparePowers(Power.strength, 
					response.getHeroOne().getName(), 
					response.getHeroOne().getPowerStats().getStrength(), 
					response.getHeroTwo().getName(),
					response.getHeroTwo().getPowerStats().getStrength());
			response.setComparativeStrength(comparativeResult);
			break;
			
		default:
			break;
		}
	}

	private void comparePowers(Power power, String heroOneName, 
			int heroOneCoefficient, String heroTwoName, int heroTwoCoefficient) {
		
		comparativeResult = "";
		crossDataResult = heroOneCoefficient - heroTwoCoefficient;
		
		if(heroOneCoefficient > heroTwoCoefficient) {
			comparativeResult = "( + ) "+heroOneName + " has more "+power.name()+
					" than "+heroTwoName+" by "+crossDataResult+" points";
		} 
		else if(heroOneCoefficient == heroTwoCoefficient) {
			comparativeResult = "( = ) "+heroOneName + " has equal "+power.name()+
					" than "+heroTwoName+" by "+crossDataResult+" points";
			
		} 
		else if(heroOneCoefficient < heroTwoCoefficient) {
			comparativeResult = "( - ) "+heroOneName + " has less "+power.name()+
					" than "+heroTwoName+" by "+crossDataResult+" points";
			
		}
		
	}

}
