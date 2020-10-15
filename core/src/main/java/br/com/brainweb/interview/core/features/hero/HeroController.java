package br.com.brainweb.interview.core.features.hero;

import java.util.List;

import javax.validation.Valid;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.brainweb.interview.core.features.hero.body.HeroRequest;
import br.com.brainweb.interview.core.features.hero.body.HeroResponse;
import br.com.brainweb.interview.core.mapper.ListDozerBeanMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "api/v1/heroes")
@Api(tags = { "Heroes" }, description = "Hero management resources")
public class HeroController {
	
	@Autowired
	private HeroService heroService;
	
	
	@PostMapping
	@ApiOperation(value = "Save heroes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<String> save(@RequestBody @Valid HeroRequest request) {
		heroService.save(request);
		return new ResponseEntity<String>(HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@ApiOperation(value = "Update heroes")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<String> update(@PathVariable String id, @RequestBody @Valid HeroRequest request) {
		heroService.update(request, id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete heroes")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<String> delete(@PathVariable String id) {
		heroService.delete(id);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	@GetMapping
	@ApiOperation(value = "List all heroes")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<List<HeroResponse>> getHeroes() {
		List<HeroResponse> response = 
				new ListDozerBeanMapper().mapAsList(heroService.getHeroes(), 
						HeroResponse.class);
		return new ResponseEntity<List<HeroResponse>>(response, HttpStatus.OK);
	}
	

	@GetMapping("/{id}")
	@ApiOperation(value = "Search heroes by id")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<HeroResponse> getHeroesById(@PathVariable String id) {
		HeroResponse response = 
				new DozerBeanMapper().map(heroService.getHeroById(id), 
						HeroResponse.class);
		return new ResponseEntity<HeroResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping("name/{name}")
	@ApiOperation(value = "Search heroes by nome")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<HeroResponse> getHeroesByName(@PathVariable String name) {
		HeroResponse response = 
				new DozerBeanMapper().map(heroService.getHeroByName(name), 
						HeroResponse.class);
		return new ResponseEntity<HeroResponse>(response, HttpStatus.OK);
	}
	
}
