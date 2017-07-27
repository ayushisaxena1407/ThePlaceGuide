package com.saxena.ThePlaceGuide.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell pc on 7/6/2017.
 */

public class Location implements Serializable
{

    @SerializedName("position")
    @Expose
    private List<Double> position = null;
    @SerializedName("address")
    @Expose
    private Address address;
    private final static long serialVersionUID = 7912729247832921437L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Location() {
    }

    /**
     *
     * @param position
     * @param address
     */
    public Location(List<Double> position, Address address) {
        super();
        this.position = position;
        this.address = address;
    }

    public List<Double> getPosition() {
        return position;
    }

    public void setPosition(List<Double> position) {
        this.position = position;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}


