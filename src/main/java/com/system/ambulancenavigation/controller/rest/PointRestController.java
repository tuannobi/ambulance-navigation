package com.system.ambulancenavigation.controller.rest;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.system.ambulancenavigation.model.Point;
import com.system.ambulancenavigation.model.json.NearestPoint;
import com.system.ambulancenavigation.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rest")
public class PointRestController {
    private final PointService pointService;

    @Autowired
    public PointRestController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/points")
    List<Point> getPoints() {
        return pointService.getPoints();
    }

    @GetMapping("/points/nearest")
    Map<String,Object> getNearestPoint(@RequestParam Double point){
        Object object = pointService.getNearestPoint(point);
        Map<String,Object> map=new HashMap<>();
        map.put("pointId", ((Object[])object)[0]);
        map.put("distance", ((Object[])object)[1]);
        return map;
    }

    @GetMapping("/roads/best")
    Object getBestRoad(@RequestParam Integer startPoint, Integer endPoint, String timeString){

        return pointService.getBestRoad(startPoint,endPoint,timeString);
    }

}
