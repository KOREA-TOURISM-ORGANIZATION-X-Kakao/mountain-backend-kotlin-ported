FROM openjdk:8-jdk
LABEL maintainer="wsnam0507@gmail.com"
VOLUME /tmp
ARG JAR_FILE=./build/libs/mountain-go-kotlin.jar
COPY ${JAR_FILE} mountain-app-kotlin.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mountain-app-kotlin.jar"]