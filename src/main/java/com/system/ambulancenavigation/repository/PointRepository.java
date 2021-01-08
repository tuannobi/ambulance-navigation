package com.system.ambulancenavigation.repository;

import com.system.ambulancenavigation.model.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PointRepository extends CrudRepository<Point, Long> {
    @Query(value="select * from point;", nativeQuery = true)
    List<Point> getPoints();

    @Query(value = "select p.point_id, p.x, p.y from point p join point_arc pc on p.point_id = pc.point_id where pc.arc_id = :arcId", nativeQuery = true)
    List<Object> getPointsByArc(Integer arcId);

    @Query(value = "SELECT point_id, x, y, sqrt(pow((?1-x),2)+pow((?2-y),2)) as distance " +
            "from point group by point_id ORDER by distance ASC LIMIT 1", nativeQuery = true)
    Object getNearestPoint(Double x, Double y);

    @Query(value="SELECT * from point where x= :x and y= :y",nativeQuery = true)
    Point getPointById(Double x, Double y);


    @Query(value = "select a.arc_id, sum(sqrt(pow((start_x-end_x),2)+pow((start_y-end_y),2))) as distance\n" +
            "from (SELECT arc_id,point_arc_id as ida,point.point_id as start,x as start_x,y as start_y \n" +
            "from point inner join point_arc on point.point_id= point_arc.point_id) a inner join \n" +
            "(SELECT arc_id,point_arc_id as idb,point.point_id as end,x as end_x,y as end_y \n" +
            "from point inner join point_arc on point.point_id= point_arc.point_id) b \n" +
            "on a.ida = b.idb-1 and a.arc_id = b.arc_id and a.arc_id in (select arc_id \n" +
            "from arc where start_point_id in (select point_id from point where x = :startPointX \n" +
            "and y = :startPointY) and end_point_id in \n" +
            "(select point_id from point where x = :endPointX and y = :endPointY )) \n" +
            "and a.arc_id not in (select arc_id from point_arc inner join \n" +
            "point_time on point_arc.point_id= point_time.point_id \n" +
            "where time_id in (select timeid from time where start_time <= :startTime \n" +
            "and end_time > :endTime)) group by a.arc_id order by distance asc", nativeQuery = true)
    List<Object> getRoads(Double startPointX, Double startPointY, Double endPointX, Double endPointY, String startTime, String endTime);

    @Query(value = "select distinct(arc_id) from point_arc inner join\n" +
            "point_time on point_arc.point_id= point_time.point_id\n" +
            "where time_id in (select timeid from time \n" +
            "where start_time <= :startTime and end_time > :endTime) \n" +
            "and arc_id in (select arc_id from arc \n" +
            "where start_point_id in (select point_id from point \n" +
            "where x = :startPointX and y = :startPointY) \n" +
            "and end_point_id in (select point_id from point where x = :endPointX \n" +
            "and y = :endPointY))", nativeQuery = true)
    List<Object> getBadRoads(Double startPointX, Double startPointY, Double endPointX, Double endPointY, String startTime, String endTime);



}