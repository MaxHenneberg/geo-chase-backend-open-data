package boundary;

import boundary.to.StopTO;
import control.Mapper;
import entity.be.StopBE;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
@Resource
@Path("/opendata")
@Slf4j
public class OpenDataResource {
    @Inject
    CsvReader csvReader;
    @Inject
    Mapper mapper;
    @Inject
    EntityManager em;

    @GET
    @Path("/init")
    public void startInit() throws FileNotFoundException {
        final var mappingMap = new HashMap<String, String>();
        mappingMap.put("stop_id", "stopId");
        mappingMap.put("stop_name", "stopName");
        mappingMap.put("stop_lat", "stopLat");
        mappingMap.put("stop_lon", "stopLon");
        mappingMap.put("stop_url", "stopUrl");
        mappingMap.put("platform_code", "platformCode");
        final var mappingResult = csvReader.convertFromCsv("stops.txt", StopTO.class, mappingMap);
        final var stops = mappingResult.stream().map(mapper::mapStop).collect(Collectors.toList());
        this.persistMappedObjects(stops);
        final var stations = stops.stream().filter(distinctByKey(StopBE::getStopStation)).collect(Collectors.toSet());
        this.persistMappedObjects(stations.stream().map(mapper::stopToStation).collect(Collectors.toSet()));
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @Transactional
    public <T> void persistMappedObjects(Collection<T> mappedBEs) {
        log.info("Received {} BEs", mappedBEs.size());
        for (T mappedBE : mappedBEs) {
            em.merge(mappedBE);
        }
        log.info("Import done");
    }
}
