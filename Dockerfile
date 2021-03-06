# SE API SERVER DOCKERFILE

# Start with a base image containing Java runtime
FROM openjdk:11 as build

# Add Author info
LABEL maintainer="se@kumoh.ac.kr"

# Add a volume to
VOLUME /var/se-auth-server

# Make port 8060 available to the world outside this container
EXPOSE 8060

# The application's jar file
ARG JAR_FILE=build/libs/authserver-0.0.1-SNAPSHOT.jar
ARG CONFIG=src/main/resources/application.yml

# Add the application's jar to the container
ADD ${JAR_FILE} authserver-0.0.1-SNAPSHOT.jar
ADD ${CONFIG} application.yml

COPY . .

# Add Timezone
ENV TZ=Asia/Seoul

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/authserver-0.0.1-SNAPSHOT.jar"]