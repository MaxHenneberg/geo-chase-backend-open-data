package boundary.to;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StopTimesTO {
    @CsvBindByName(column = "trip_id")
    String tripId;
    @CsvBindByName(column = "arrival_time")
    String arrivalTime;
    @CsvBindByName(column = "departure_time")
    String departureTime;
    @CsvBindByName(column = "stop_id")
    String stopId;
    @CsvBindByName(column = "stop_sequence")
    Integer stopSequence;
    @CsvBindByName(column = "stop_headsign")
    String stopHeadsign;
    @CsvBindByName(column = "pickup_type")
    Integer pickupType;
    @CsvBindByName(column = "drop_off_type")
    Integer dropOffType;
    @CsvBindByName(column = "shape_dist_traveled")
    Double shapeDistTraveled;
}
