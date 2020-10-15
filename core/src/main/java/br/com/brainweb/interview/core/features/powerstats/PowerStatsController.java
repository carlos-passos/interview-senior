package br.com.brainweb.interview.core.features.powerstats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.brainweb.interview.core.features.powerstats.body.PowerStatsResponse;
import br.com.brainweb.interview.core.mapper.ListDozerBeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "api/v1/power-stats")
@Api(tags = { "Power Stats" }, description = "Power stats management resources")
public class PowerStatsController {
	
	@Autowired
	private PowerStatsService powerStatsService;
	
	
	@GetMapping
	@ApiOperation(value = "List all power stats")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<List<PowerStatsResponse>> getPowerStats() {
		List<PowerStatsResponse> response = 
				new ListDozerBeanMapper().mapAsList(powerStatsService.getPowerStats(), 
						PowerStatsResponse.class);
		return new ResponseEntity<List<PowerStatsResponse>>(response, HttpStatus.OK);
	}
	
}
