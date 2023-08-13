FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/patient-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} patient.jar
ENTRYPOINT ["java","-jar","/patient.jar"]