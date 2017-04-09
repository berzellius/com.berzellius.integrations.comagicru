package com.berzellius.integrations.comagicru.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by berz on 26.03.2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ComagicResponse {

    protected Boolean success;
    protected String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
