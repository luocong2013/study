package com.zync.commander;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;

/**
 * 注解驱动的参数解析
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/10/11 10:16
 */
public class Args {

    @Parameter(names = {"-log", "-verbose"}, description = "Level of verbosity")
    private boolean verbose = false;

    @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
    private String groups;

    @Parameter(names = "-debug", description = "Debug mode")
    private boolean debug = false;

    @Parameter(names = {"-port", "-p"}, description = "Port number", validateWith = PositiveInteger.class, required = true)
    private int port = 8080;

    public boolean isVerbose() {
        return verbose;
    }

    public String getGroups() {
        return groups;
    }

    public boolean isDebug() {
        return debug;
    }

    public int getPort() {
        return port;
    }
}
