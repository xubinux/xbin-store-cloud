# Name:     xbin-store-cloud-config
# Time:     2017-05-07

FROM java:8-jre-alpine

MAINTAINER Binux <xu.binux@gmail.com>

RUN mkdir /app

WORKDIR /app

COPY xbin-store-cloud-config-1.0.0.jar /app

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/xbin-store-cloud-config-1.0.0.jar"]

EXPOSE 8502