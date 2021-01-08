package com.system.ambulancenavigation.repository;

import com.system.ambulancenavigation.model.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PointRepository extends CrudRepository<Point, Long> {
    @Query(value="select * from point;", nativeQuery = true)
    List<Point> getPoints();

    @Query(value = "SELECT point_id,sqrt(pow((?1-x),2)+pow((?1-y),2)) as distance " +
            "from point group by point_id ORDER by distance ASC LIMIT 1", nativeQuery = true)
    Object getNearestPoint(Double point);

    @Query(value="select * from (SELECT arc.arc_id, count(*) as time1 from arc " +
            "inner join point_arc on arc.arc_id= point_arc.arc_id where start_point = ?1 " +
            "and end_point = ?2 and status = 'good' and time = ?3 group by " +
            "arc.arc_id having count() >= all (select count(*) from arc inner join " +
            "point_arc on arc.arc_id= point_arc.arc_id where start_point = ?1 and end_point = ?2 " +
            "and status = 'good' and time = ?3 group by arc.arc_id)) " +
            "a join (select arc_id, sum(estimating_time) estimating_time from point_arc " +
            "where time = ?3 group by arc_id) b on a.arc_id = b.arc_id order by estimating_time asc",nativeQuery = true)
    Object getBestRoad(Integer startPoint, Integer endPoint, String time);
}