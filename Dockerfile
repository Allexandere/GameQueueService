FROM openjdk:11.0.11-jdk-slim
WORKDIR /workdir
COPY game-queue-manager-1.0.0.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]