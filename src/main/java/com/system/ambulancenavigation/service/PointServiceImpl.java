package com.system.ambulancenavigation.service;

import com.system.ambulancenavigation.base.BaseServiceImpl;
import com.system.ambulancenavigation.model.Point;
import com.system.ambulancenavigation.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PointServiceImpl extends BaseServiceImpl<Point, Long> implements PointService {
  private final PointRepository pointRepository;

  @Autowired
  public PointServiceImpl(PointRepository pointRepository){
    this.pointRepository=pointRepository;
  }

  @Override
  protected CrudRepository<Point, Long> getRepository() {
    return this.pointRepository;
  }
}
