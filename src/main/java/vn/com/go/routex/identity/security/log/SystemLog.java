package vn.com.go.routex.identity.security.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

@Slf4j
public class SystemLog {

    private final Logger logger;

    public SystemLog(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public static SystemLog getLogger(Class<?> clazz) {
        return new SystemLog(clazz);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();
    public Object[] processArgs(Object... args) {
        if (args == null || args.length == 0) {
            return new String[0];
        }

        return Arrays.stream(args)
                .map(this::toSafeString)
                .toArray();
    }

    private Object toSafeString(Object arg) {
        if (arg == null) return null;
        if (arg instanceof CharSequence cs) return cs.toString();
        if (arg instanceof Number || arg instanceof Boolean || arg instanceof Enum) return String.valueOf(arg);
        if (arg instanceof Throwable t) return stackTraceOf(t);

        try {
            return objectMapper.writeValueAsString(arg);
        } catch (Exception e) {
            log.info(e.getMessage());
            return String.valueOf(arg);
        }
    }

    private String stackTraceOf(Throwable t) {
        var sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public void info(String message, Object... args) {
        if (logger.isInfoEnabled()) {
            logger.info(message, processArgs(args));
        }
    }
}
