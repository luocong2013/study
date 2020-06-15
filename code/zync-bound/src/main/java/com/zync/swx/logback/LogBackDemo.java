package com.zync.swx.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LC
 * @version V1.0.0
 * @project luoc-hanlp
 * @package com.luo.logback
 * @description TODO
 * @date 2017-12-7 15:01
 */
public class LogBackDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogBackDemo.class);
    public static void main(String[] args){
        LOGGER.trace("TRACE日志");
        LOGGER.debug("DEBUG日志");
        LOGGER.info("INFO日志");
        LOGGER.warn("WARN日志");
        LOGGER.error("ERROE日志");
    }
}
