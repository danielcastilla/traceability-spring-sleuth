package com.dancas.traceability.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class RestService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;


    public ResponseEntity<Map> getNumber(String decimal){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> map = new HashMap<String, String>();
        map.put("decimal", decimal);

        HttpEntity<Map<String, String>> request = new HttpEntity<Map<String, String>>(map, headers);

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate.postForEntity(env.getProperty("service.endpoint"), request, Map.class);

    }

    public String doGet(){
        return restTemplate.exchange(env.getProperty("service.endpoint"), HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
        }).getBody();
    }

}
