# Name:     xbin-store-cloud-service-cart
# Time:     2017-05-07

FROM java:8-jdk

MAINTAINER Binux <xu.binux@gmail.com>

RUN mkdir /app

WORKDIR /app

COPY xbin-store-cloud-service-cart-1.0.0.jar /app

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/xbin-store-cloud-service-cart-1.0.0.jar"]

EXPOSE 8517