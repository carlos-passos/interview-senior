package br.com.brainweb.interview.core.features.crosspowers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.brainweb.interview.core.features.crosspowers.body.CrossPowersRequest;
import br.com.brainweb.interview.core.features.crosspowers.body.CrossPowersResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins={"*"})
@RequestMapping(value = "api/v1/cross-powers")
@Api(tags = { "Cross of Powers" }, description = "Cross of powers management resources")
public class CrossPowersController {
	
	@Autowired
	private CrossPowersService crossPowersService;
	
	
	@PostMapping
	@ApiOperation(value = "Cross of powers heroes")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<CrossPowersResponse> consolidatedData(@RequestBody @Valid CrossPowersRequest request) {
		return new ResponseEntity<CrossPowersResponse>(crossPowersService.consolidatedData(request), HttpStatus.OK);
	}
	

	
}
