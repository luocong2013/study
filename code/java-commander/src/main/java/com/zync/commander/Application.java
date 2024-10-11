package com.zync.commander;

import com.beust.jcommander.JCommander;

/**
 * 启动类
 *
 * @author luocong
 * @version v2.5.0
 * @since 2024/10/11 10:38
 */
public class Application {

    public static void main(String[] args) {
        Args arguments = new Args();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(args);

        // 使用解析后的参数
        if (arguments.isVerbose()) {
            System.out.println("Verbose mode enabled");
        }
        if (arguments.getGroups() != null) {
            System.out.println("Groups: " + arguments.getGroups());
        }
        if (arguments.isDebug()) {
            System.out.println("Debug mode enabled");
        }

        System.out.println("Port: " + arguments.getPort());
    }

}
