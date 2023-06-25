package control;

import entity.be.StationBE;
import entity.be.StopBE;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
@Slf4j
public class DataAggregator {
    @Inject
    EntityManager em;
    @Inject
    Mapper mapper;

    public void aggregateData() {
        final var stops = em.createQuery("Select s from StopBE s", StopBE.class).getResultList();
        log.info("Received {} stops", stops.size());
        final var stations = this.aggregateStopsToStation(stops);
        this.persistStation(stations);
    }

    private Collection<StationBE> aggregateStopsToStation(Collection<StopBE> stopBES) {
        final var stopMap = new HashMap<String, Set<StopBE>>();
        stopBES.forEach(stopBE -> stopMap.compute(stopBE.getStopStation(), (key, oldValue) -> {
            if (oldValue == null) {
                final var nSet = new HashSet<StopBE>();
                nSet.add(stopBE);
                return nSet;
            } else {
                oldValue.add(stopBE);
                return oldValue;
            }
        }));
        final var output = new HashSet<StationBE>();
        for (Map.Entry<String, Set<StopBE>> stringSetEntry : stopMap.entrySet()) {
            output.add(this.mapper.stopToStation(stringSetEntry.getKey(), stringSetEntry.getValue()));
        }
        return output;
    }

    @Transactional
    void persistStation(Collection<StationBE> stationBES) {
        log.info("Persisting {} stations", stationBES.size());
        for (StationBE stationBE : stationBES) {
            em.persist(stationBE);
        }
        em.flush();
    }
}
