package com.system.ambulancenavigation.model;

import javax.persistence.*;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "description")
    private String description;

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
