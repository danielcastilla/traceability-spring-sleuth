package com.dancas.traceability;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootApplication
public class Microservice3Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservice3Application.class, args);
	}
}
@RestController
class Microservice3Controller{
	
	@Autowired
	RestTemplate restTemplate;
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}@Bean
	public AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}
	private static final Logger LOG = Logger.getLogger(Microservice3Controller.class.getName());
	
	@GetMapping(value="/ms3")
	public String microservice3() {
		LOG.info("Inside microservice 3..");
        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		String response = (String) restTemplate.exchange("http://localhost:8084/ms4", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        }).getBody();
		return " -> 3 "+response;
	}


    @PostMapping(path = "/ms3")
    public Map getNumbers(@RequestBody MultiValueMap<String, String> map){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String decimal = String.valueOf(map.get("decimal").get(0));
        map.add("octal", convert(Integer.parseInt(decimal)));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("http://localhost:8084/ms4", request, Map.class);

        return response.getBody();
    }


	//Convert to octal
	private String convert(int n) {
		return Integer.toOctalString(n);
	}

}

