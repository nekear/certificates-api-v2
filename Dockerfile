# --> JAR BUILDING STAGE <-- #
FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /workspace/app

# Copying the files needed for maven build
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src/main/java src/main/java
COPY src/test src/test

# Building the jar
RUN mvn install -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# --> JAR RUNNING STAGE <-- #
FROM eclipse-temurin:21-jdk-alpine

# Needed for Spring for faster writing speed
VOLUME /tmp

# Copying layered jar data
COPY --from=builder /workspace/app/target/dependency/BOOT-INF/lib /app/lib
COPY --from=builder /workspace/app/target/dependency/META-INF /app/META-INF
COPY --from=builder /workspace/app/target/dependency/BOOT-INF/classes /app

COPY src/main/resources/* /app

EXPOSE 8080

ENTRYPOINT ["java","-cp","app:app/lib/*","com.github.nekear.certificates_api.ApiApplication"]



