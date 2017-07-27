package com.saxena.ThePlaceGuide.DetailsPojo;

import com.saxena.ThePlaceGuide.Pojo.Address;

import java.util.ArrayList;

/**
 * Created by dell pc on 7/24/2017.
 */

public class DetailsLocation {
    Address address;
    ArrayList<Access> access;

    public DetailsLocation(Address address, ArrayList<Access> access) {
        this.address = address;
        this.access = access;

    }




    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ArrayList<Access> getAccess() {
        return access;
    }

    public void setAccess(ArrayList<Access> access) {
        this.access = access;
    }
}
