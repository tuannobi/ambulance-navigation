package com.system.ambulancenavigation.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.system.ambulancenavigation.base.BaseServiceImpl;
import com.system.ambulancenavigation.model.Point;
import com.system.ambulancenavigation.model.json.NearestPoint;
import com.system.ambulancenavigation.repository.PointRepository;
import jdk.nashorn.internal.ir.ObjectNode;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;

@Service
public class PointServiceImpl extends BaseServiceImpl<Point, Long> implements PointService {
    private final PointRepository pointRepository;

    @Autowired
    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    protected CrudRepository<Point, Long> getRepository() {
        return this.pointRepository;
    }

    @Override
    public List<Point> getPoints() {
        return pointRepository.getPoints();
    }

    @Override
    public Object getNearestPoint(Double point) {
        return pointRepository.getNearestPoint(point);
    }

    @Override
    public Object getBestRoad(Integer startPoint, Integer endPoint, String time){
        return pointRepository.getBestRoad(startPoint,endPoint,time);
    }
}
