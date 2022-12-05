FROM adoptopenjdk/openjdk11

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
CMD ["chmod", "+x", "./gradlew"]
CMD ["./gradlew", "build", "--exclude-task", "test"]

COPY build/libs/target/*.jar app.jar

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod" ,"app.jar"]