package boundary.to;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class StopTO {
    @CsvBindByName(column = "stop_id")
    String stopId;
    @CsvBindByName(column = "stop_name")
    String stopName;
    @CsvBindByName(column = "stop_lat")
    String stopLat;
    @CsvBindByName(column = "stop_lon")
    String stopLon;
    @CsvBindByName(column = "stop_url")
    String stopUrl;
    @CsvBindByName(column = "platform_code")
    String platformCode;
}
