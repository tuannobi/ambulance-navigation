package com.system.ambulancenavigation.controller.rest;

import com.system.ambulancenavigation.model.Point;
import com.system.ambulancenavigation.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/points")
public class PointRestController {
    private PointService pointService;

    @Autowired
    public PointRestController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public List<Point> getAll() {
        return pointService.findAll();
    }

    @GetMapping("/getPointsByArc")
    List<Point> getListPointByArc(@RequestParam("startX") Double startPointX,
                                  @RequestParam("endX") Double endPointX,
                                  @RequestParam("startY") Double startPointY,
                                  @RequestParam("endY") Double endPointY) {
        return pointService.getListPointByArc(startPointX, endPointX, startPointY, endPointY);
    }


}
