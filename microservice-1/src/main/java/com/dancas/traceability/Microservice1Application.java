package com.dancas.traceability;

import brave.Tracer;
import com.dancas.traceability.service.RestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.*;

@SpringBootApplication
public class Microservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservice1Application.class, args);
	}
}

@RestController
@Api
class Microservice1Controller{

	@Autowired
    RestService microservice1Service;

	@Autowired
    Tracer tracer;

	private static Logger log = LoggerFactory.getLogger(Microservice1Controller.class);

	@ApiOperation(consumes = "application/json", produces = "application/json", value = " Valor del nombre ", notes = " Get Operation ", httpMethod = "GET", response = Map.class)
	@GetMapping(value="/ms1")
    @RequestMapping(value = "/ms1", method = RequestMethod.GET, produces = "application/json")
	public String microservice1() {

		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String response = microservice1Service.doGet();
		return " Ha pasado por los microservicios: 1 "+ response;
	}

	@ApiOperation(consumes = "application/json", produces = "application/json", value = " Valor del nombre ", notes = " Post Operation ", httpMethod = "POST", response = Map.class)
	@PostMapping(path = "/ms1/{decimal}")
    @RequestMapping(value = "/ms1/{decimal}", method = RequestMethod.POST, produces = "application/json")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "decimal", value = "", required = true, dataType = "string", paramType = "query")
	})
	public Map getNumbers(@PathVariable String decimal){
        log.info("Handling microservice 1 Controller");
		ResponseEntity<Map> response = microservice1Service.getNumber(decimal);
		return response.getBody();
	}

}
