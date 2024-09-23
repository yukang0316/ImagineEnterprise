FROM openjdk:21-jdk

# Install necessary packages
#RUN apt-get update && apt-get install -y openjdk-21-jdk netcat-traditional

# Create working directory
WORKDIR /app

# Copy the application JAR file
COPY build/libs/imagine-0.0.1-SNAPSHOT.jar app.jar

# Copy the wait-for-it script
COPY wait-for-it.sh /wait-for-it.sh

# Make wait-for-it script executable
RUN chmod +x /wait-for-it.sh

# Specify the command to run the application
CMD ["sh", "-c", "/wait-for-it.sh mysql_container:3306 --timeout=180 --strict -- java -jar app.jar"]