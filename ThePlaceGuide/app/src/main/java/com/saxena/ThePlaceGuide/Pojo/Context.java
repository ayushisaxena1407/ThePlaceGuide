package com.saxena.ThePlaceGuide.Pojo;

/**
 * Created by dell pc on 7/6/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Context implements Serializable
{

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("href")
    @Expose
    private String href;
    private final static long serialVersionUID = 223671633559064547L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Context() {
    }

    /**
     *
     * @param location
     * @param type
     * @param href
     */
    public Context(Location location, String type, String href) {
        super();
        this.location = location;
        this.type = type;
        this.href = href;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}