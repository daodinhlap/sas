FROM hub.mtop.vn/centos7-jre8
MAINTAINER Do Dong Duc <duc.do@homedirect.com.vn>
ARG JAR_FILE
VOLUME /tmp
WORKDIR /opt
COPY target/${JAR_FILE} app.jar
RUN mkdir config && mkdir logs
COPY src/main/resources/*.properties src/main/resources/log4j2.xml config/
ENTRYPOINT ["/opt/jre8/bin/java"]
CMD ["-Dlogging.config=file:./config/log4j2.xml", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar", "--spring.config.location=file:./config/application.properties,file:./config/bootstrap.properties"]