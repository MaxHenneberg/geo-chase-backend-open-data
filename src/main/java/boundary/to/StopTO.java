package boundary.to;

import lombok.Data;

@Data
public class StopTO {
    String stopId;
    String stopName;
    String stopLat;
    String stopLon;
    String stopUrl;
    String platformCode;
}
