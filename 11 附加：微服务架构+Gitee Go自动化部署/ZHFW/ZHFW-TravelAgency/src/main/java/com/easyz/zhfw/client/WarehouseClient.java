package com.easyz.zhfw.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WarehouseClient {

    private static final String BASE_URL = "http://localhost:8081/warehouse";

    @Autowired
    private RestTemplate restTemplate_xml;

     @Autowired
    private RestTemplate restTemplate;

    public int query(String resourceID) {
        ResponseEntity<Integer> response = restTemplate_xml.getForEntity(
                BASE_URL + "/query?resourceID={resourceID}",
                Integer.class,
                resourceID
        );
        return response.getBody();
    }

    public boolean pickupItems(String resourceID, int amount) {
        ResponseEntity<Boolean> response = restTemplate_xml.getForEntity(
                BASE_URL + "/pickupItems?resourceID={resourceID}&amount={amount}",
                Boolean.class,
                resourceID, amount
        );
        return response.getBody();
    }

    public int complementStock(String resourceID, int amount) {
        ResponseEntity<Integer> response = restTemplate_xml.getForEntity(
                BASE_URL + "/complementStock?resourceID={resourceID}&amount={amount}",
                Integer.class,
                resourceID, amount
        );
        return response.getBody();
    }

    public String holdItems(String resourceID, int amount) {
        ResponseEntity<String> response = restTemplate.getForEntity(
                BASE_URL + "/holdItems?resourceID={resourceID}&amount={amount}",
                String.class,
                resourceID, amount
        );
        return response.getBody();
    }

    public void cancelHoldingItems(String holdingID) {
        restTemplate_xml.getForEntity(
                BASE_URL + "/cancelHoldingItems?holdingID={holdingID}",
                Void.class,
                holdingID
        );
    }

    public boolean pickupHoldingItems(String holdingID) {
        ResponseEntity<Boolean> response = restTemplate_xml.getForEntity(
                BASE_URL + "/pickupHoldingItems?holdingID={holdingID}",
                Boolean.class,
                holdingID
        );
        return response.getBody();
    }
}
