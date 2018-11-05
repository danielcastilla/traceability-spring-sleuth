package com.dancas.traceability;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Microservices1Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservices1Application.class, args);
	}
}

@RestController
class Microservices1Controller{
	
	@Autowired
	RestTemplate restTemplate;
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}@Bean
	public AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}
	private static final Logger LOG = Logger.getLogger(Microservices1Controller.class.getName());
	
	@GetMapping(value="/ms1")
	public String microservice1() {
		LOG.info("Inside microservices 1..");
		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 String response = (String) restTemplate.exchange("http://localhost:8082/ms2", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
	        }).getBody();
		return " Ha pasado por los microservicios: 1 "+ response;
	}

	@PostMapping(path = "/ms1/{decimal}")
	public Map getNumbers(@PathVariable String decimal){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("decimal", decimal);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8082/ms2/", request, Map.class);

		return response.getBody();
	}




}
