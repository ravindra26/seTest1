package com.service1.pojo;

/**
 *
 * @author Ravindra
 */
public class CardDetails {

    private Number number;
    private String scheme;
    private String type;
    private String brand;
    private Boolean prepaid;
    private Country country;
    private Bank bank;

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getPrepaid() {
        return prepaid;
    }

    public void setPrepaid(Boolean prepaid) {
        this.prepaid = prepaid;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

}
