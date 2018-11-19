package com.dancas.traceability;

import brave.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
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
    Tracer tracer;

	@Autowired
	RestTemplate restTemplate;
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	private static Logger log = LoggerFactory.getLogger(Microservice1Controller.class);

	@GetMapping(value="/ms1")
	public String microservice1() {

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
        log.info("Handling microservice 1 Controller");


        HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, String> map = new HashMap<String, String>();
		map.put("decimal", decimal);

		HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(map, headers);

		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8082/ms2/", request, Map.class);
		return response.getBody();

        //Map result = new HashMap<String, String>(1);
        //result.put("ok", "ok");
        //return result;

	}

}
