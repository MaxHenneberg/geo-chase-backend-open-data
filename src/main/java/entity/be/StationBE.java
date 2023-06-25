package entity.be;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_STATION")
@Data
public class StationBE {
    @Column(name = "id")
    @SequenceGenerator(name = "station_gen", sequenceName = "S_STATION_ID")
    @GeneratedValue(generator = "station_gen", strategy = GenerationType.SEQUENCE)
    public Long id;
    @Id
    @Column(name = "STOP_STATION")
    public String stopStation;
    @Column(name = "STOP_NAME")
    public String stopName;
    @Column(name = "STATION_LAT")
    public Double stationLat;
    @Column(name = "STATION_LON")
    public Double stationLon;
}
