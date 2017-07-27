package com.saxena.ThePlaceGuide.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell pc on 7/6/2017.
 */

public class Results implements Serializable {

    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    private final static long serialVersionUID = -3527789416410820996L;

    /**
     * No args constructor for use in serialization
     */
    public Results() {
    }

    /**
     * @param items
     */
    public Results(List<Item> items) {
        super();
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
