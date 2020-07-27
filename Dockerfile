FROM openjdk:8-jdk-alpine
MAINTAINER raj_upadhyay@hotmail.com
VOLUME /tmp
EXPOSE 8080
ADD libs/messanger-0.0.1-SNAPSHOT.jar messanger.jar
ENTRYPOINT ["java","-jar","messanger.jar"]