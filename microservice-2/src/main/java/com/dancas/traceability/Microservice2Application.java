package com.dancas.traceability;

import brave.Span;
import brave.Tracer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
public class Microservice2Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservice2Application.class, args);
	}
}
@RestController
class Microservice2Controller{

	@Autowired
	Tracer tracer;

	@Autowired
	RestTemplate restTemplate;
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	private static final Logger LOG = Logger.getLogger(Microservice2Controller.class.getName());
	
	@GetMapping(value="/ms2")
	public String microserviceService2() {
		LOG.info("Inside microservice 2..");
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String response = (String) restTemplate.exchange("http://localhost:8083/ms3", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        }).getBody();
		return " -> 2 " + response;
	}

	@PostMapping(path = "/ms2")
	public Map getNumbers(@RequestBody Map<String, String> map){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String decimal = String.valueOf(map.get("decimal"));
		int decim = Integer.parseInt(decimal);
		map.put("binario", convert(decim));

		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(map, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8083/ms3", request, Map.class);

		return response.getBody();
	}

	//Convert to binario
	private String convert(int n) {

		Span newSpan = this.tracer.nextSpan().name("convert-number()");
		String binary = "";

		try {
			newSpan.tag("number", String.valueOf(n));

			newSpan.annotate("convert-number");
			binary = Integer.toBinaryString(n);
		}finally {
            newSpan.finish();
		}

		return binary;
	}

}