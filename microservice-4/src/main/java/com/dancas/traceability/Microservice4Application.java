package com.dancas.traceability;

import brave.Span;
import brave.Tracer;
import io.swagger.annotations.Api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Api
class Microservice4Controller {

    @Autowired
    Tracer tracer;

	@Autowired
	RestTemplate restTemplate;

	private static final Logger LOG = Logger.getLogger(Microservice4Controller.class.getName());

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@GetMapping(value = "/ms4")
	@RequestMapping(value = "/ms4", method = RequestMethod.GET, produces = "application/json")
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
    @RequestMapping(value = "/ms4/{decimal}", method = RequestMethod.POST, produces = "application/json")
    public Map getNumbers(@RequestBody Map<String, String> map){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String decimal = String.valueOf(map.get("decimal"));
        map.put("hexadecimal", convert(Integer.parseInt(decimal)));

        return map;
    }

	//Convert to hexadecimal
	private String convert(int n) {

        Span newSpan = this.tracer.nextSpan().name("convert-number()");
        String hex = "";

        try {
            newSpan.tag("number", String.valueOf(n));

            newSpan.annotate("convert-number");
            hex = Integer.toHexString(n);
        }finally {
            newSpan.finish();
        }

        return hex;

	}



}