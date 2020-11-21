package com.system.ambulancenavigation.service;

import com.system.ambulancenavigation.base.BaseService;
import com.system.ambulancenavigation.model.Point;

import java.util.List;

public interface PointService extends BaseService<Point, Long> {
    List<Point> getListPointByArc(Double startPointX, Double endPointX, Double startPointY, Double endPointY);
}
