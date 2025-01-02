# Use an appropriate JDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the application JAR file
COPY build/libs/abcem-all.jar abcem.jar

# Expose the ports your application runs on and the debug port
EXPOSE 8080 5005

# Run the application with remote debugging enabled
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "abcem.jar"]
