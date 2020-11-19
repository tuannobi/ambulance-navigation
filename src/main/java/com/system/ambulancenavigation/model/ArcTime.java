package com.system.ambulancenavigation.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "arc_time")
public class ArcTime {
  @Column(name = "time")
  private LocalDateTime time;

  @Column(name = "string")
  private String status;

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
