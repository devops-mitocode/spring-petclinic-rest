FROM eclipse-temurin:17-jre-alpine
WORKDIR /workspace
COPY target/spring-petclinic-rest-*.jar app.jar
EXPOSE 9966
ENTRYPOINT [ "java", "-jar", "/workspace/app.jar" ]