package com.saxena.ThePlaceGuide.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell pc on 7/6/2017.
 */

public class Places implements Serializable
{

    @SerializedName("results")
    @Expose
    private Results results;
    @SerializedName("search")
    @Expose
    private Search search;
    private final static long serialVersionUID = -1718588240744623995L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Places() {
    }

    /**
     *
     * @param results
     * @param search
     */
    public Places(Results results, Search search) {
        super();
        this.results = results;
        this.search = search;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

}

