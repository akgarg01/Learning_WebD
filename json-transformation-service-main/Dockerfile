# Use public Maven image from Docker Hub
FROM maven:3-jdk-11 as builder
ARG ARTIFACT_VERSION=0.0.1-SNAPSHOT
RUN rm -rf /build && \
    mkdir -p /build && \
    mkdir -p /downloads
WORKDIR /build
COPY . /build
RUN mvn clean verify -DskipTests -Dartifact.version=${ARTIFACT_VERSION}

# Use public OpenJDK image from Docker Hub
FROM openjdk:11.0.5-jre-slim
ARG ARTIFACT_VERSION=0.0.1-SNAPSHOT
WORKDIR /usr/local/
COPY --from=builder /build/json-transformation-service/target/service-${ARTIFACT_VERSION}.jar /usr/local/json-transformation-service.jar
COPY --from=builder /build/database/target/database-${ARTIFACT_VERSION}.jar /usr/local/database.jar
CMD java ${JVM_OPTS} -jar /usr/local/json-transformation-service.jar