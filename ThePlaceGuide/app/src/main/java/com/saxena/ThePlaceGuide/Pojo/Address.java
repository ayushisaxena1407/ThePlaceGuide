package com.saxena.ThePlaceGuide.Pojo;

/**
 * Created by dell pc on 7/6/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable
{
    String state;

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("house")
    @Expose
    private String house;
    @SerializedName("street")
    @Expose
    private String street;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("county")
    @Expose
    private String county;
    @SerializedName("stateCode")
    @Expose
    private String stateCode;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    private final static long serialVersionUID = -9140794260994727163L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Address() {
    }

    /**
     *
     * @param text
     * @param postalCode
     * @param county
     * @param street
     * @param countryCode
     * @param stateCode
     * @param house
     * @param district
     * @param country
     * @param city
     */
    public Address(String text, String house, String street, String postalCode, String district, String city, String county, String stateCode, String country, String countryCode, String state) {
        super();
        this.text = text;
        this.house = house;
        this.street = street;
        this.postalCode = postalCode;
        this.district = district;
        this.city = city;
        this.county = county;
        this.stateCode = stateCode;
        this.country = country;
        this.countryCode = countryCode;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

}