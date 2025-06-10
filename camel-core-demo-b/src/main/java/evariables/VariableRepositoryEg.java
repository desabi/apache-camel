package evariables;

import org.apache.camel.spi.VariableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class VariableRepositoryEg implements VariableRepository {

    public static final String ID = "variableRepositoryEg";

    // could be a database, redis, etc.
    private static final Map<String, Object> store = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger(VariableRepositoryEg.class);

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Object removeVariable(String name) {
        log.info("Removing variable: {}", name);
        return store.remove(name);
    }

    @Override
    public Object getVariable(String name) {
        log.info("Fetching variable: {}", name);
        return store.get(name);
    }

    @Override
    public void setVariable(String name, Object value) {
        log.info("Storing variable: {} with value {}", name, value);
        store.put(name, value);
    }

    // to initialize or stop the store configuration
    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
