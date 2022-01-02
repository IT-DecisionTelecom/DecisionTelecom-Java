package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Viber message source type
 */
public enum ViberMessageSourceType {
    @SerializedName("1") PROMOTIONAL (1),
    @SerializedName("2") TRANSACTIONAL (2);

    private int type;

    private ViberMessageSourceType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
