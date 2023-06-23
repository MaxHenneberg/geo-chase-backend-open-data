package entity.be;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "T_STATION")
@Data
public class StationBE {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "station_gen", sequenceName = "S_STATION_ID")
    @GeneratedValue(generator = "station_gen", strategy = GenerationType.SEQUENCE)
    public Long id;
    @Column(name = "STOP_STATION")
    public String stopStation;
    @Column(name = "STOP_NAME")
    public String stopName;
}
