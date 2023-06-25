package control;

import boundary.CsvReader;
import boundary.to.StopTO;
import boundary.to.StopTimesTO;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.context.ManagedExecutor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

@ApplicationScoped
@Slf4j
public class OpenDataImporter {
    @Inject
    CsvReader csvReader;
    @Inject
    Mapper mapper;
    @Inject
    EntityManager em;
    @Inject
    ManagedExecutor managedExecutor;

    AtomicInteger progressCounter = new AtomicInteger(0);


    public void loadData() throws ExecutionException, InterruptedException, TimeoutException, IOException {
        this.persistsOpenDataFile("stops.txt", StopTO.class, (to) -> mapper.mapStop(to));
        this.persistsOpenDataFile("stop_times.txt", StopTimesTO.class, (to) -> mapper.mapStopTimes(to));
    }

    @Transactional
    <T, E> void persistsOpenDataFile(String fileName, Class<T> clazz,
                                     Function<T, E> mapperFunction) throws ExecutionException, InterruptedException, TimeoutException, IOException {
        this.progressCounter = new AtomicInteger(0);
        log.info("Importing {}", clazz.getSimpleName());
        final var toList = csvReader.convertFromCsv(fileName, clazz);
        log.info("Received {} TOs", toList.size());
        managedExecutor.submit(() -> toList.parallelStream().map(mapperFunction)
                .forEach(this::persistMappedObject)).get(10, TimeUnit.MINUTES);
        log.info("Import done");
    }

    @Transactional
    public <E> void persistMappedObjects(Collection<E> mappedBEs) {
        log.info("Received {} BEs", mappedBEs.size());
        for (E mappedBE : mappedBEs) {
            em.merge(mappedBE);
        }
    }

    @Transactional
    public <E> void persistMappedObject(E mappedBE) {
        em.merge(mappedBE);
        final var currentProgress = this.progressCounter.getAndIncrement();
        if (currentProgress % 100000 == 0) {
            log.info("Processed {}; Sample {}", currentProgress, mappedBE);
        }
    }


}
