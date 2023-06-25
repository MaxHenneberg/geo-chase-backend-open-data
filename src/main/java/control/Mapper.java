package control;

import boundary.to.StopTO;
import boundary.to.StopTimesTO;
import entity.be.StationBE;
import entity.be.StopBE;
import entity.be.StopTimesBE;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalTime;
import java.util.Set;

@ApplicationScoped
@Slf4j
public class Mapper {

    public StopBE mapStop(StopTO input) {
        final var output = new StopBE();
        output.setStopId(input.getStopId());
        output.setStopLat(Double.parseDouble(input.getStopLat()));
        output.setStopLon(Double.parseDouble(input.getStopLon()));
        output.setStopName(input.getStopName());
        output.setPlatformCode(input.getPlatformCode());
        output.setStopUrl(input.getStopUrl());
        output.setStopStation(this.getStopStation(input.getStopId()));
        return output;
    }

    public StopTimesBE mapStopTimes(StopTimesTO input) {
        final var output = new StopTimesBE();
        output.setTripId(input.getTripId());
        output.setArrivalTime(this.parseMvgTimes(input.getArrivalTime()));
        output.setDepartureTime(this.parseMvgTimes(input.getDepartureTime()));
        output.setStopId(input.getStopId());
        output.setStopSequence(input.getStopSequence());
        output.setStopHeadsign(input.getStopHeadsign());
        output.setPickupType(input.getPickupType());
        output.setDropOffType(input.getDropOffType());
        output.setShapeDistTraveled(input.getShapeDistTraveled());
        return output;
    }

    public StationBE stopToStation(String stationId, Set<StopBE> stops) {
        final var output = new StationBE();
        output.setStopStation(stationId);
        var aggregatedLon = 0.0;
        var aggregatedLat = 0.0;
        var counter = 0;

        for (StopBE stop : stops) {
            output.setStopName(stop.getStopName());
            aggregatedLon += stop.getStopLon();
            aggregatedLat += stop.getStopLat();
            counter++;
        }

        if (counter > 0) {
            output.setStationLon(aggregatedLon / counter);
            output.setStationLat(aggregatedLat / counter);
        } else {
            output.setStationLon(-1.0);
            output.setStationLat(-1.0);
        }


        return output;
    }

    private String getStopStation(String stopId) {
        final var splittedStopId = stopId.split(":");
        return splittedStopId[2];
    }

    private LocalTime parseMvgTimes(String timeAsString) {
        var toBeParsed = timeAsString;
        final var hour = Integer.parseInt(timeAsString.substring(0, 2));
        if (hour > 23) {
            final var fixedHour = hour - 24;
            if (fixedHour < 10) {
                toBeParsed = "0" + fixedHour + ":" + timeAsString.substring(3);
            } else {
                toBeParsed = fixedHour + ":" + timeAsString.substring(3);
            }

        }
        return LocalTime.parse(toBeParsed);
    }
}
