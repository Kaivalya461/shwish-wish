FROM amazoncorretto:21-alpine
COPY target/shwish-wish-1.0.0.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=gke,prod","-Dkv.content.key=${secret}","-jar","/app.jar"]
