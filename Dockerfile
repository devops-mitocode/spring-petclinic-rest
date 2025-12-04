# FROM eclipse-temurin:17-alpine
FROM eclipse-temurin:17-jre-alpine-3.22
# FROM amazoncorretto:17-alpine
WORKDIR /workspace
COPY target/spring-petclinic-rest-*.jar app.jar
EXPOSE 9966
ENTRYPOINT [ "java", "-jar", "/workspace/app.jar" ]