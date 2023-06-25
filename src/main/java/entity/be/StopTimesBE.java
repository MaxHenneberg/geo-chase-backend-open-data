package entity.be;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "T_STOP_TIMES")
@Data
public class StopTimesBE {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "stop_gen", sequenceName = "S_STOP_TIMES_ID")
    @GeneratedValue(generator = "stop_gen", strategy = GenerationType.SEQUENCE)
    public Long id;
    @Column(name = "TRIP_ID")
    String tripId;
    @Column(name = "ARRIVAL_TIME")
    LocalTime arrivalTime;
    @Column(name = "DEPARTURE_TIME")
    LocalTime departureTime;
    @Column(name = "STOP_ID")
    String stopId;
    @Column(name = "STOP_SEQUENCE")
    Integer stopSequence;
    @Column(name = "STOP_HEADSIGN")
    String stopHeadsign;
    @Column(name = "PICKUP_TYPE")
    Integer pickupType;
    @Column(name = "DROP_OFF_TYPE")
    Integer dropOffType;
    @Column(name = "SHAPE_DIST_TRAVELED")
    Double shapeDistTraveled;
}
