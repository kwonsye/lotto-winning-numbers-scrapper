FROM openjdk:15
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} scraping-app.jar
ENTRYPOINT ["java","-jar","/scraping-app.jar"]