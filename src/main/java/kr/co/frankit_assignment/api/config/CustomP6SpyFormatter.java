package kr.co.frankit_assignment.api.config;

import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class CustomP6SpyFormatter implements MessageFormattingStrategy {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(
            int connectionId,
            String now,
            long elapsed,
            String category,
            String prepared,
            String sql,
            String url) {
        if (sql != null && prepared != null && sql.trim().equals(prepared.trim())) {
            return null;
        }

        return String.format(
                "\n%s\n[took %dms] | [category: %s] | [connection: %d] | [time: %s] | [url: %s]",
                sql.trim(), elapsed, category, connectionId, now, url);
    }
}
