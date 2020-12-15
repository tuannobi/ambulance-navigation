package com.system.ambulancenavigation.service;

import com.system.ambulancenavigation.base.BaseService;
import com.system.ambulancenavigation.model.Point;

import java.time.LocalDateTime;
import java.util.List;

public interface PointService extends BaseService<Point, Long> {
    List<Point> getPointsByArc(Double startPointX, Double endPointX, Double startPointY, Double endPointY);
    List<Point> getPointsByArcAndTime(Double startPointX, Double endPointX, Double startPointY, Double endPointY, String localDateTime);
}
