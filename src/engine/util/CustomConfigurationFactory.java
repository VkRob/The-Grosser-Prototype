package engine.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;

import java.net.URI;

/**
 * Configuration manager for Log4j2
 * @author Ben
 */

@Plugin(name = "CustomConfigurationFactory", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class CustomConfigurationFactory extends ConfigurationFactory {

    private static Configuration createConfiguration(final String name, ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        /* internal log4j2 messages will only be displayed if they are WARN or higher */
        builder.setStatusLevel(Level.WARN);
        /* new appender to output to console */
        AppenderComponentBuilder appenderBuilder = builder.newAppender("STDOUT", "CONSOLE")
                .addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        /* pattern to apply to output to console */
        appenderBuilder.add(builder.newLayout("PatternLayout")
                /* boring pattern */
//                .addAttribute("pattern", "[%d{HH:mm:ss}] (%t) [%c{1}] %level: %m%n%throwable"));
                /* colorful pattern! */
                .addAttribute("pattern", "%style{[%d{HH:mm:ss}]}{white} %style{(%t)}{magenta} %style{[%c{1}]}{blue} %highlight{%level{length=5}: %m%n%throwable}"));
        builder.add(appenderBuilder);
        /* set up root logger to log to the console appender */
        builder.add(builder.newRootLogger(Level.TRACE).add(builder.newAppenderRef("STDOUT")));
        return builder.build();
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final String name, final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }

    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }
}