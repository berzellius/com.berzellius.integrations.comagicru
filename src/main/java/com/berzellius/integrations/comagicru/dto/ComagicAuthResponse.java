package com.berzellius.integrations.comagicru.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by berz on 26.03.2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComagicAuthResponse extends ComagicResponse {

    public ResponseData getData() {
        return data;
    }

    public void setData(ResponseData data) {
        this.data = data;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class ResponseData{
        private String session_key;

        public String getSession_key() {
            return session_key;
        }

        public void setSession_key(String session_key) {
            this.session_key = session_key;
        }
    }

    protected ResponseData data;
}
