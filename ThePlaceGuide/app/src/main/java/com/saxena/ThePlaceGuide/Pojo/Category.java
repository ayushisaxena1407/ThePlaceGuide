package com.saxena.ThePlaceGuide.Pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dell pc on 7/6/2017.
 */

public class Category implements Serializable{



        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("href")
        @Expose
        private String href;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("system")
        @Expose
        private String system;
        private final static long serialVersionUID = 6942671619088203934L;

        /**
         * No args constructor for use in serialization
         *
         */
        public Category() {
        }

        /**
         *
         * @param id
         * @param system
         * @param title
         * @param type
         * @param href
         */
        public Category(String id, String title, String href, String type, String system) {
            super();
            this.id = id;
            this.title = title;
            this.href = href;
            this.type = type;
            this.system = system;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

    }

