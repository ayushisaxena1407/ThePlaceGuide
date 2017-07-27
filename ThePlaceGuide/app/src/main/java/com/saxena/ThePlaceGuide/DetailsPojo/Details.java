package com.saxena.ThePlaceGuide.DetailsPojo;

import java.util.ArrayList;

/**
 * Created by dell pc on 7/24/2017.
 */

public class Details {
    String name;
    DetailsLocation location;
    DetailsContacts contacts;
    ArrayList<DetailsCategories> categories;

    public Details(String name, DetailsLocation location, DetailsContacts contacts, ArrayList<DetailsCategories> categories) {
        this.name = name;
        this.location = location;
        this.contacts = contacts;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailsLocation getLocation() {
        return location;
    }

    public void setLocation(DetailsLocation location) {
        this.location = location;
    }

    public DetailsContacts getContacts() {
        return contacts;
    }

    public void setContacts(DetailsContacts contacts) {
        this.contacts = contacts;
    }

    public ArrayList<DetailsCategories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<DetailsCategories> categories) {
        this.categories = categories;
    }
}
