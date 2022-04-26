package com.zync.logback.kafka.logging.appender;

import net.logstash.logback.composite.JsonProvider;
import net.logstash.logback.composite.JsonProviders;
import net.logstash.logback.encoder.LogstashEncoder;

import java.util.ArrayList;

/**
 * The type Enhanced logstash encoder
 *
 * @author luocong
 * @version v1.0
 * @date 2022/4/26 17:39
 */
public class EnhancedLogstashEncoder extends LogstashEncoder {

    /**
     * set exclude provider
     *
     * @param excludedProviderClassName the excluded provider class name
     */
    public void setExcludeProvider(String excludedProviderClassName) {
        JsonProviders<?> providers = getFormatter().getProviders();
        for (JsonProvider<?> provider : new ArrayList<>(providers.getProviders())) {
            if (provider.getClass().getName().equals(excludedProviderClassName)) {
                providers.removeProvider((JsonProvider) provider);
            }
        }
    }

}
