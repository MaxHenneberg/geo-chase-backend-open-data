package boundary;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
@Slf4j
public class CsvReader {
    private final static String BASE_PATH = "/opendata/";


    public <T> List<T> convertFromCsv(String fileName, Class<T> toClazz, HashMap<String, String> mappingStrategy) throws FileNotFoundException {
        final var fileStream = this.getClass().getResourceAsStream(BASE_PATH + fileName);
        if (fileStream != null) {
            CsvToBeanBuilder<T> beanBuilder = new CsvToBeanBuilder<>(new InputStreamReader(fileStream, StandardCharsets.UTF_8));
            if (!mappingStrategy.isEmpty()) {
                HeaderColumnNameTranslateMappingStrategy<T> strategy = new HeaderColumnNameTranslateMappingStrategy<T>();
                strategy.setType(toClazz);
                strategy.setColumnMapping(mappingStrategy);
                beanBuilder.withMappingStrategy(strategy);
            } else {
                beanBuilder.withType(toClazz);
            }
            return beanBuilder.build().parse();
        } else {
        }
        return Collections.emptyList();
    }
}
