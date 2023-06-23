package control;

import boundary.to.StopTO;
import entity.be.StationBE;
import entity.be.StopBE;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
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

    public StationBE stopToStation(StopBE stop){
        final var output = new StationBE();
        output.setStopStation(stop.getStopStation());
        output.setStopName(stop.getStopName());
        return output;
    }

    private String getStopStation(String stopId) {
        final var splittedStopId = stopId.split(":");
        return splittedStopId[2];
    }
}
