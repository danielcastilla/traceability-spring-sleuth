package com.dancas.traceability;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
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
	}@Bean
	public AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
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
	public Map getNumbers(@RequestBody MultiValueMap<String, String> map){

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		String decimal = String.valueOf(map.get("decimal").get(0));
		int decim = Integer.parseInt(decimal);
		map.add("binario", convert(decim));

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8083/ms3", request, Map.class);

		return response.getBody();
	}

	//Convert to binario
	private String convert(int n) {

		Span newSpan = this.tracer.createSpan("convert-number()");
		String binary = "";

		try {
			this.tracer.addTag("number", String.valueOf(n));

			newSpan.logEvent("convert-number");
			binary = Integer.toBinaryString(n);
		}finally {
			this.tracer.close(newSpan);
		}

		return binary;
	}

}