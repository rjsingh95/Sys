package com.supplyingyourservice.ranjeet.singh.sys.elastic;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



    @IgnoreExtraProperties
    public class bookobject {

        @SerializedName("hits")
        @Expose
        private hitlis hits;

        public hitlis getHits() {
            return hits;
        }

        public void setHits(hitlis hits) {
            this.hits = hits;
        }
    }


