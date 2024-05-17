package com.electrosalaf.sqsspringsample.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * @author Ibrahim Lawal
 * @createdOn 15/March/2024
 */
@SuppressWarnings("ALL")
@Service
@Slf4j
public class ApiClientService {

    private final RestTemplate restTemplate;

    public ApiClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private <T, V> ResponseEntity<T> getResponse(String url, V request, Class<T> clazz, HttpHeaders httpHeaders, HttpMethod httpMethod) {
        log.trace(">>>>>>>> making {} request to : {} with payload : {} >>>>>>>>>> " ,httpMethod.name(), url, request);

        ResponseEntity<T> entity = restTemplate.exchange(url, httpMethod, getHttpEntity(request, httpHeaders), clazz);

        log.trace(">>>>>>>> received response from : {} with response body  : {}  >>>>>>>>>> " , url, entity.getBody());
        return entity;
    }

    public <T,U> ResponseEntity<T> postRequestWithHeaders(String url, U request, HttpHeaders httpHeaders, Class<T> clazz){
        return getResponse(url, request, clazz, httpHeaders, HttpMethod.POST);
    }

    private  <V> HttpEntity<?> getHttpEntity(V request, HttpHeaders httpHeaders) {
        return new HttpEntity<>(request, httpHeaders);
    }


    public static String generateBasicAuth(String clientId, String clientSecret) {
        String clientIdSecretString = clientId + ":" + clientSecret;
        return "Basic " + Base64.getEncoder().encodeToString(clientIdSecretString.getBytes(StandardCharsets.UTF_8));
    }
}
