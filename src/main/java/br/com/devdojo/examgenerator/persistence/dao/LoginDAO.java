package br.com.devdojo.examgenerator.persistence.dao;

import br.com.devdojo.examgenerator.annotation.ExceptionHandler;
import br.com.devdojo.examgenerator.custom.CustomRestTemplate;
import br.com.devdojo.examgenerator.persistence.model.support.Token;
import br.com.devdojo.examgenerator.util.JsonUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import java.io.Serializable;

import static org.springframework.http.HttpMethod.POST;

public class LoginDAO implements Serializable {
    private final String BASE_URL = "http://localhost:8088/login";
    private final CustomRestTemplate restTemplate;
    private final JsonUtil jsonUtil;

    @Inject
    public LoginDAO(CustomRestTemplate restTemplate, JsonUtil jsonUtil) {
        this.restTemplate = restTemplate;
        this.jsonUtil = jsonUtil;
    }

    @ExceptionHandler
    public Token loginReturningToken(String username, String password) {
        String loginJson = "{\"username\":" + addQuotes(username)
                + ",\"password\":" + addQuotes(password) + "}";
        //tratando login inválido
        ResponseEntity<Token> tokenExchange = restTemplate
                .exchange(BASE_URL, POST, new HttpEntity<>(loginJson, jsonUtil.createJsonHeader()), Token.class);
        return tokenExchange.getBody();
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    private String addQuotes(String value) {
        return new StringBuilder(300).append("\"").append(value).append("\"").toString();
    }

}
