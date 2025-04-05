package com.easyz.zhfw.vo;

import java.util.Map;

public class TravelAgencyResponseType {

    private boolean successful;
    private Map<String, String> itineraries; // 存储不同类型的行程及其对应的 shippingID
    private String message;

    // Getters and Setters

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public Map<String, String> getItineraries() {
        return itineraries;
    }

    public void setItineraries(Map<String, String> itineraries) {
        this.itineraries = itineraries;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
