package com.service1.pojo;

/**
 *
 * @author Ravindra
 */
public class Country {

    private int numeric;
    private String alpha2;
    private String name;
    private String currency;
    private Double latitude;
    private Double longitude;

    public int getNumeric() {
        return numeric;
    }

    public void setNumeric(int numeric) {
        this.numeric = numeric;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
