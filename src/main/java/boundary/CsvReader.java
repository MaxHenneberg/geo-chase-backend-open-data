package boundary;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
@Slf4j
public class CsvReader {
    private final static String BASE_PATH = "/opendata/";


    public <T> List<T> convertFromCsv(String fileName, Class<T> toClazz) throws IOException {
        final var fileStream = this.getClass().getResourceAsStream(BASE_PATH + fileName);
        if (fileStream != null) {
            var input = new String(fileStream.readAllBytes(), StandardCharsets.UTF_8);
            if (input.startsWith("\uFEFF")) {
                log.info("Removed BOM");
                input = input.substring(1);
            }
            CsvToBeanBuilder<T> beanBuilder = new CsvToBeanBuilder<>(
                    new InputStreamReader(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
            beanBuilder.withType(toClazz);
            return beanBuilder.build().parse();
        }
        return Collections.emptyList();
    }
}
