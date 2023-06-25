package boundary;

import control.DataAggregator;
import control.OpenDataImporter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@ApplicationScoped
@Resource
@Path("/opendata")
@Slf4j
public class OpenDataResource {
    @Inject
    OpenDataImporter openDataImporter;
    @Inject
    DataAggregator dataAggregator;

    @GET
    @Path("/load")
    public void loadData() throws ExecutionException, InterruptedException, TimeoutException, IOException {
        this.openDataImporter.loadData();
    }

    @GET
    @Path("/aggregate")
    public void aggregateData() {
        this.dataAggregator.aggregateData();
    }


}
