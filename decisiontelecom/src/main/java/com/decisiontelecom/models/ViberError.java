package com.decisiontelecom.models;

import com.google.gson.annotations.SerializedName;

/**
 * Represents error which may occur while working with Viber messages
 */
public class ViberError {
    @SerializedName("name") private final String name;
    @SerializedName("message") private final String message;
    @SerializedName("code") private final int code;
    @SerializedName("status") private final int status;

    public ViberError(String name, String message, int code, int status) {
        this.name = name;
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public ViberError(String name, int status) {
        this.name = name;
        this.message = "";
        this.code = 0;
        this.status = status;
    }
    
    /** 
     * Gets the error name
     * 
     * @return Error name
     */
    public String getName() {
        return name;
    }

    /** 
     * Gets error message
     * 
     * @return Erorr message
     */
    public String getMessage() {
        return message;
    }
    
    /** 
     * Gets error code
     * 
     * @return Erorr code
     */
    public int getCode() {
        return code;
    }
    
    /** 
     * Gets error status
     * 
     * @return Error status
     */
    public int getStatus() {
        return status;
    }
}
