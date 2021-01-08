package com.system.ambulancenavigation.controller.rest;

import com.system.ambulancenavigation.model.Point;
import com.system.ambulancenavigation.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
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
    Map<String,Object> getNearestPoint(@RequestParam Double x, @RequestParam Double y){
        Object object = pointService.getNearestPoint(x, y);
        Map<String,Object> map=new HashMap<>();
        map.put("pointId", ((Object[])object)[0]);
        map.put("distance", ((Object[])object)[1]);
        return map;
    }

    @GetMapping("/points/{x}/{y}")
    Point getPointId(@PathVariable Double x, @PathVariable Double y){
        return pointService.getPointById(x, y);
    }

    @GetMapping("/roads")
    public List<Map<String, Object>> getRoads(
                                 @RequestParam Double startPointX,
                                 @RequestParam Double startPointY,
                                 @RequestParam Double endPointX,
                                 @RequestParam Double endPointY,
                                 @RequestParam String startTime,
                                 @RequestParam String endTime){
        Object nearestStartPoint = pointService.getNearestPoint(startPointX,startPointY);
//        Object nearestEndPoint = pointService.getNearestPoint(endPointX,endPointY);
        Point startPoint = new Point();
        startPoint.setX((Double) ((Object[])nearestStartPoint)[1]);
        startPoint.setY((Double) ((Object[])nearestStartPoint)[2]);
        System.out.println("Point is " + startPoint.toString());
        List<Object> objects = pointService.getRoads(startPoint.getX(),startPoint.getY(), endPointX, endPointY, startTime, endTime);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(int i=0; i<objects.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("arcId",((Object[])objects.get(i))[0]);
            map.put("distance",((Object[])objects.get(i))[1]);
            maps.add(map);
        }
        return maps;
    }

    @GetMapping("/roads/arc")
    public List<Map<String, Object>> getPointsByArc(@RequestParam Integer arcId){
        List<Object> objects = pointService.getPointsByArc(arcId);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(int i=0; i<objects.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("pointId",((Object[])objects.get(i))[0]);
            map.put("x",((Object[])objects.get(i))[1]);
            map.put("y",((Object[])objects.get(i))[2]);
            maps.add(map);
        }
        return maps;
    }

//    @GetMapping("/roads/bad")
//    public List<Map<String, Object>> getBadRoads(
//            @RequestParam Double startPointX,
//            @RequestParam Double startPointY,
//            @RequestParam Double endPointX,
//            @RequestParam Double endPointY,
//            @RequestParam String startTime,
//            @RequestParam String endTime){
//        Object nearestStartPoint = pointService.getNearestPoint(startPointX,startPointY);
////        Object nearestEndPoint = pointService.getNearestPoint(endPointX,endPointY);
//        Point startPoint = new Point();
//        startPoint.setX((Double) ((Object[])nearestStartPoint)[1]);
//        startPoint.setY((Double) ((Object[])nearestStartPoint)[2]);
//        System.out.println("=========================================Point is " + startPoint.toString());
//        List<Object> objects = pointService.getBadRoads(startPoint.getX(),startPoint.getY(), endPointX, endPointY, startTime, endTime);
//        List<Map<String,Object>> maps = new ArrayList<>();
//        for(int i=0; i<objects.size(); i++) {
//            Map<String, Object> map = new HashMap<>();
//            map.put("arcId",((Object[])objects.get(i))[0]);
//            maps.add(map);
//        }
//        return maps;
//    }
    @GetMapping("/roads/bad")
    public List<Map<String,Object>> getBadRoads(
            @RequestParam Double startPointX,
            @RequestParam Double startPointY,
            @RequestParam Double endPointX,
            @RequestParam Double endPointY,
            @RequestParam String startTime,
            @RequestParam String endTime){
        Object nearestStartPoint = pointService.getNearestPoint(startPointX,startPointY);
        Point startPoint = new Point();
        startPoint.setX((Double) ((Object[])nearestStartPoint)[1]);
        startPoint.setY((Double) ((Object[])nearestStartPoint)[2]);

        System.out.println("=========================================Point is " + startPoint.toString());
        List<Object> objects = pointService.getBadRoads(startPoint.getX(),startPoint.getY(), endPointX, endPointY, startTime, endTime);
        List<Map<String,Object>> maps = new ArrayList<>();
        for(int i=0; i<objects.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("arcId",(objects.get(i)));
            maps.add(map);
        }
        return maps;
//        return pointService.getBadRoads(startPoint.getX(),startPoint.getY(),endPointX,endPointY,startTime,endTime);
    }

}
