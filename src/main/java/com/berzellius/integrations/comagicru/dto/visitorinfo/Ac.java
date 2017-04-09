package com.berzellius.integrations.comagicru.dto.visitorinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by berz on 26.03.2017.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ac{
    public Ac() {
    }

    private Long id;
    private String name;
    private String state;
    private Boolean is_removed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getIs_removed() {
        return is_removed;
    }

    public void setIs_removed(Boolean is_removed) {
        this.is_removed = is_removed;
    }
}
