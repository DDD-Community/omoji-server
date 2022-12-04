FROM adoptopenjdk/openjdk11
CMD ["./gradlew", "build", "--exclude-task", "test"]
ARG JAR_FILE_PATH=./build/libs/*.jar
COPY ${JAR_FILE_PATH} app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod" ,"app.jar"]