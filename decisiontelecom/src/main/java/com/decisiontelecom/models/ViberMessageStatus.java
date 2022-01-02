package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Viber message status
 */
public enum ViberMessageStatus {
    @SerializedName("0") SENT (0),
    @SerializedName("1") DELIVERED (1),
    @SerializedName("2") ERROR (2),
    @SerializedName("3") REJECTED (3),
    @SerializedName("4") UNDELIVERED (4),
    @SerializedName("5") PENDING (5),
    @SerializedName("20") UNKNOWN (20),;

    private final int status;

    private ViberMessageStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }    
}
