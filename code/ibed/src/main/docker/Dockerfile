# Version: 1.0.0
FROM java

MAINTAINER luocong<luocong2017@gmail.com>

EXPOSE 7070

WORKDIR /opt

COPY *.jar app.jar

ENV LC_ALL zh_CN.UTF-8

ENTRYPOINT ["sh", "-c", "java $PARAMS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]