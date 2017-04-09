package com.berzellius.integrations.comagicru.dto;

import com.berzellius.integrations.comagicru.dto.visitorinfo.VisitorInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by berz on 26.03.2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComagicVisitorInfoResponse extends ComagicResponse {
    protected VisitorInfo data;

    public VisitorInfo getData() {
        return data;
    }

    public void setData(VisitorInfo data) {
        this.data = data;
    }
}
