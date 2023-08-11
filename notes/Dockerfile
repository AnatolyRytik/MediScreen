FROM openjdk:11
VOLUME /tmp
EXPOSE 8082
ARG JAR_FILE=target/notes-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} notes.jar
ENTRYPOINT ["java","-jar","/notes.jar"]