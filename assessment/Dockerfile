FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
ARG JAR_FILE=target/assessment-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} assessment.jar
ENTRYPOINT ["java","-jar","/assessment.jar"]