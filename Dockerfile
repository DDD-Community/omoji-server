FROM adoptopenjdk/openjdk11

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN echo ${GCP_ACCOUNT} > ./src/main/resources/omoji-server-account.json
RUN ["ls","src/main/resources"]
RUN cat ./src/main/resources/omoji-server-account.json

RUN chmod +x ./gradlew
RUN ./gradlew build --exclude-task test

RUN ["ls","-al"]
RUN ["ls","build/libs"]

RUN cp ./build/libs/*.jar ./app.jar
RUN ["ls","-al"]

EXPOSE 9090
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod" ,"app.jar"]