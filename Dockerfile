FROM maven:3.8.6-jdk-11-slim AS build
COPY . /app
WORKDIR /app
RUN mvn -f pom.xml clean package -DskipTests

# Dockerfile version
FROM openjdk:11-jre-slim
EXPOSE 8080
COPY --from=build /app/target/FreelancerApp-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
ENTRYPOINT ["java","-jar","app.jar"]