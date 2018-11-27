package com.dancas.traceability;

import brave.Tracer;
import com.dancas.traceability.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication
public class Microservice1Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservice1Application.class, args);
	}
}

@RestController
class Microservice1Controller{

	@Autowired
    RestService microservice1Service;

	@Autowired
    Tracer tracer;

	private static Logger log = LoggerFactory.getLogger(Microservice1Controller.class);

	@GetMapping(value="/ms1")
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

	@PostMapping(path = "/ms1/{decimal}")
	public Map getNumbers(@PathVariable String decimal){
        log.info("Handling microservice 1 Controller");
		ResponseEntity<Map> response = microservice1Service.getNumber(decimal);
		return response.getBody();
	}

}
