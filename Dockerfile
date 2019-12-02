# Start with a base image containing Java runtime (mine java 8)
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="artem.bogomaz@wizeline.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file (when packaged)
ARG JAR_FILE=target/apac-take-home-test-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} apac-take-home-test.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/apac-take-home-test.jar"]