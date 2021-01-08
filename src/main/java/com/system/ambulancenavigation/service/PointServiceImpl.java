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
    public Object getNearestPoint(Double x, Double y) {
        return pointRepository.getNearestPoint(x,y);
    }

    public Point getPointById(Double x, Double y){
        return pointRepository.getPointById(x, y);
    }

    public List<Object> getRoads(Double startPointX, Double startPointY, Double endPointX, Double endPointY, String startTime, String endTime){
        return pointRepository.getRoads(startPointX,startPointY,endPointX,endPointY,startTime,endTime);
    }
    public List<Object> getPointsByArc(Integer arcId){
        return pointRepository.getPointsByArc(arcId);
    }

    public List<Object> getBadRoads(Double startPointX, Double startPointY, Double endPointX, Double endPointY, String startTime, String endTime){
        return pointRepository.getBadRoads(startPointX, startPointY, endPointX, endPointY, startTime, endTime);
    }

}
