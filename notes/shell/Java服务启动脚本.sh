#! /bin/bash

JAVA_OPTS="$JAVA_OPTS -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:+PrintGC -XX:GCLogFileSize=20M -Xloggc:/temp/logs/ops-gc-%t.log -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70"

nohup java $JAVA_OPTS -Xmx1024m -Xms1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=128m -Dprocess.name=app-name -Dlogging.path=/temp/logs -Dspring.config.location=/app/application.yml,/app/application.properties -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n -jar /app/app-name.jar > /dev/null 2>&1 &
