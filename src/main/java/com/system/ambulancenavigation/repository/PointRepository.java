package com.system.ambulancenavigation.repository;

import com.system.ambulancenavigation.model.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PointRepository extends CrudRepository<Point, Long> {

    @Query(value = "select * from\n"
            + "point join point_arc a on point.point_id=a.point_id \n"
            + "join (select arc_id from arc where start_point_x=?1\n"
            + "and end_point_x=?2\n"
            + "and start_point_y=?3\n"
            + "and end_point_y=?4) b on a.arc_id=b.arc_id", nativeQuery = true)
    List<Point> getPointsByArc(Double startPointX, Double endPointX, Double startPointY, Double endPointY);

    @Query(value = "select * from point join point_arc a on point.point_id=a.point_id\n" +
            "join (select arc_id from arc where start_point_x=?1\n" +
            "and end_point_x=?2\n" +
            "and start_point_y=?3\n" +
            "and end_point_y=?4) b on a.arc_id=b.arc_id\n" +
            "and time=?5", nativeQuery = true)
    List<Point> getPointsByArcAndTime(Double startPointX, Double endPointX, Double startPointY, Double endPointY, LocalDateTime localDateTime);

//    List<Point> getPointsByPolygon();

}
