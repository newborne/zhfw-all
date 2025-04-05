package com.easyz.zhfw.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PaymentClient {

    private static final String BASE_URL = "http://localhost:8081/payment";

    @Autowired
    private RestTemplate restTemplate_xml;

    public int queryAccount(String accountID) {
        ResponseEntity<Integer> response = restTemplate_xml.getForEntity(
                BASE_URL + "/queryAccount?accountID={accountID}",
                Integer.class,
                accountID
        );
        return response.getBody();
    }

    public boolean transfer(String accountID1, String accountID2, int amount) {
        ResponseEntity<Boolean> response = restTemplate_xml.getForEntity(
                BASE_URL + "/transfer?accountID1={accountID1}&accountID2={accountID2}&amount={amount}",
                Boolean.class,
                accountID1, accountID2, amount
        );
        return response.getBody();
    }
}
