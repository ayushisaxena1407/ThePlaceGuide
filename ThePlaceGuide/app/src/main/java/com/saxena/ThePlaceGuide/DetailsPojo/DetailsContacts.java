package com.saxena.ThePlaceGuide.DetailsPojo;

import java.util.ArrayList;

/**
 * Created by dell pc on 7/24/2017.
 */

public class DetailsContacts {

    ArrayList<PhoneD> phone;

    ArrayList<PhoneD> website;

    public ArrayList<PhoneD> getWebsite() {
        return website;
    }

    public void setWebsite(ArrayList<PhoneD> website) {
        this.website = website;
    }

    public DetailsContacts(ArrayList<PhoneD> phone) {
        this.phone = phone;
    }

    public ArrayList<PhoneD> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<PhoneD> phone) {
        this.phone = phone;
    }
}
