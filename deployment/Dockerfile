FROM openjdk:8-jre-alpine

COPY *.jar /lib/app.jar
CMD ["java", "-jar", "/lib/app.jar", "--spring.profiles.active=dev"]
