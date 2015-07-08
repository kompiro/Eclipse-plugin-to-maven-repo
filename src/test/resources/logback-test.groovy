import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.INFO

final consoleAppender = 'console'

scan('30 seconds')

appender(consoleAppender, ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = '%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n'
    }
}

root(INFO, [consoleAppender])
