FROM amazoncorretto:21-alpine
COPY target/shwish-wish-*.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=gke,prod","-Dkv.content.key=${secret}","-Dspring.mail.username=${mail-username}","-Dspring.mail.password=${mail-password}","-Dkv.mail.recipient=${mail-recipient}","-jar","/app.jar"]
