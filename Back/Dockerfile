FROM anapsix/alpine-java

RUN mkdir -p /opt/back
COPY target/*.jar /opt/back/app.jar

CMD ["java", "-jar", "/opt/back/app.jar"]

