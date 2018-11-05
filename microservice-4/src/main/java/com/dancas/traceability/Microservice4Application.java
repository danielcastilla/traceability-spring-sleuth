package com.dancas.traceability;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootApplication
public class Microservice4Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservice4Application.class, args);
	}
}

@RestController
class Microservice4Controller {

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public AlwaysSampler alwaysSampler() {
		return new AlwaysSampler();
	}

	private static final Logger LOG = Logger.getLogger(Microservice4Controller.class.getName());

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@GetMapping(value = "/ms4")
	public String zipkinService1() {
		LOG.info("Inside microservice 4..");
        try {
            Thread.sleep(4 * 1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return " -> 4 ";
	}

    @PostMapping(path = "/ms4")
    public Map getNumbers(@RequestBody MultiValueMap<String, String> map){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String decimal = String.valueOf(map.get("decimal").get(0));
        map.add("hexadecimal", convert(Integer.parseInt(decimal)));

        return map;
    }

	//Convert to hexadecimal
	private String convert(int n) {
		return Integer.toHexString(n);
	}



}