package entity.be;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_STOP")
@Data
public class StopBE {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "stop_gen", sequenceName = "S_STOP_ID")
    @GeneratedValue(generator = "stop_gen", strategy = GenerationType.SEQUENCE)
    public Long id;
    @Column(name = "STOP_ID")
    public String stopId;
    @Column(name = "STOP_STATION")
    public String stopStation;
    @Column(name = "STOP_NAME")
    public String stopName;
    @Column(name = "STOP_LAT")
    public Double stopLat;
    @Column(name = "STOP_LON")
    public Double stopLon;
    @Column(name = "STOP_URL")
    public String stopUrl;
    @Column(name = "PLATFORM_CODE")
    public String platformCode;
}
