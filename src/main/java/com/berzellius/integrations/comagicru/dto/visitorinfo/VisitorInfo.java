package com.berzellius.integrations.comagicru.dto.visitorinfo;

import com.berzellius.integrations.comagicru.deserializer.ComagicVisitorInfoAcsDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.ArrayList;

/**
 * Created by berz on 26.03.2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VisitorInfo {

    public VisitorInfo() {
    }

    @JsonDeserialize(using = ComagicVisitorInfoAcsDeserializer.class)
    protected ArrayList<Ac> acs;

    public ArrayList<Ac> getAcs() {
        return acs;
    }

    public void setAcs(ArrayList<Ac> acs) {
        this.acs = acs;
    }
}
