package com.system.ambulancenavigation.model;

import javax.persistence.*;

@Entity
@Table(name = "point_type")
public class PointType {
  @Id
  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
