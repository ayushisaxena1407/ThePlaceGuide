package com.saxena.ThePlaceGuide.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell pc on 7/6/2017.
 */

public class Search implements Serializable
{

    @SerializedName("context")
    @Expose
    private Context context;
    private final static long serialVersionUID = -7281301295306460040L;

    /**
     * No args constructor for use in serialization
     *
     */
    public Search() {
    }

    /**
     *
     * @param context
     */
    public Search(Context context) {
        super();
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
