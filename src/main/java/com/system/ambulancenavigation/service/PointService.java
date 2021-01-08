package com.system.ambulancenavigation.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.system.ambulancenavigation.base.BaseService;
import com.system.ambulancenavigation.model.Point;
import com.system.ambulancenavigation.model.json.NearestPoint;
import jdk.nashorn.internal.ir.ObjectNode;

import java.time.LocalDateTime;
import java.util.List;

public interface PointService extends BaseService<Point, Long> {
    List<Point> getPoints();

    Object getNearestPoint(Double point);
    Object getBestRoad(Integer startPoint, Integer endPoint, String time);
}
