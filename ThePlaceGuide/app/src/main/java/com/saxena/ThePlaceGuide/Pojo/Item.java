package com.saxena.ThePlaceGuide.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell pc on 7/6/2017.
 */

public class Item implements Serializable
{

    @SerializedName("position")
    @Expose
    private List<Double> position = null;
    @SerializedName("bbox")
    @Expose
    private List<Double> bbox = null;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;
    @SerializedName("having")
    @Expose
    private List<Object> having = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("authoritative")
    @Expose
    private Boolean authoritative;
    @SerializedName("averageRating")
    @Expose
    private Double averageRating;
    private final static long serialVersionUID = 5032541652528205205L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Item() {
    }

    /**
     *
     * @param position
     * @param icon
     * @param averageRating
     * @param type
     * @param authoritative
     * @param id
     * @param distance
     * @param title
     * @param category
     * @param vicinity
     * @param bbox
     * @param having
     * @param href
     */
    public Item(List<Double> position, List<Double> bbox, Double distance, String title, Category category, String icon, String vicinity, List<Object> having, String type, String href, String id, Boolean authoritative, Double averageRating) {
        super();
        this.position = position;
        this.bbox = bbox;
        this.distance = distance;
        this.title = title;
        this.category = category;
        this.icon = icon;
        this.vicinity = vicinity;
        this.having = having;
        this.type = type;
        this.href = href;
        this.id = id;
        this.authoritative = authoritative;
        this.averageRating = averageRating;
    }

    public List<Double> getPosition() {
        return position;
    }

    public void setPosition(List<Double> position) {
        this.position = position;
    }

    public List<Double> getBbox() {
        return bbox;
    }

    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public List<Object> getHaving() {
        return having;
    }

    public void setHaving(List<Object> having) {
        this.having = having;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAuthoritative() {
        return authoritative;
    }

    public void setAuthoritative(Boolean authoritative) {
        this.authoritative = authoritative;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

}


