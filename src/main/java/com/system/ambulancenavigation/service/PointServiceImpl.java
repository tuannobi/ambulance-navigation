package com.system.ambulancenavigation.service;

import com.system.ambulancenavigation.base.BaseServiceImpl;
import com.system.ambulancenavigation.model.Point;
import com.system.ambulancenavigation.repository.PointRepository;
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
    public List<Point> getPointsByArc(Double startPointX, Double endPointX, Double startPointY, Double endPointY) {
        return pointRepository.getPointsByArc(startPointX, endPointX, startPointY, endPointY);
    }

    @Override
    public List<Point> getPointsByArcAndTime(Double startPointX, Double endPointX, Double startPointY, Double endPointY, String time){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime=LocalDateTime.parse(time,formatter);
        System.out.println(localDateTime.toString());
        return pointRepository.getPointsByArcAndTime(startPointX,endPointX,startPointY,endPointY,localDateTime);
    }
}
