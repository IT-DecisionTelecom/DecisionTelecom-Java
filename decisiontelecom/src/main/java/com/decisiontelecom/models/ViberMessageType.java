package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents Viber message type
 */
public enum ViberMessageType {
    @SerializedName("106") TEXT_ONLY (106),
    @SerializedName("108") TEXT_IMAGE_BUTTON (108),
    @SerializedName("206") TEXT_ONLY_TWO_WAY (206),
    @SerializedName("208") TEXT_IMAGE_BUTTON_TWO_WAY (208);

    private final int type;

    private ViberMessageType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
