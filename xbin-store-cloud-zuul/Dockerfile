# Name:     xbin-store-cloud-zuul
# Time:     2017-05-07

FROM java:8-jre-alpine

MAINTAINER Binux <xu.binux@gmail.com>

RUN mkdir /app

WORKDIR /app

COPY xbin-store-cloud-zuul-1.0.0.jar /app

ADD http://on2bs9q7q.bkt.clouddn.com/wait-for-it.sh /

RUN chmod +x /wait-for-it.sh

ENTRYPOINT ["./wait-for-it.sh", "eureka1:8503","--", "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/xbin-store-cloud-zuul-1.0.0.jar"]

EXPOSE 8502