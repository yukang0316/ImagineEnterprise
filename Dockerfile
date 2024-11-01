#FROM openjdk:21-jdk
#
## Install necessary packages
##RUN apt-get update && apt-get install -y openjdk-21-jdk netcat-traditional
#
## Create working directory
#WORKDIR /app
#
## Copy the application JAR file
#COPY build/libs/imagine-0.0.1-SNAPSHOT.jar app.jar
#
## Copy the wait-for-it script
#COPY wait-for-it.sh /wait-for-it.sh
#
## Make wait-for-it script executable
#RUN chmod +x /wait-for-it.sh
#
## Specify the command to run the application
#CMD ["sh", "-c", "/wait-for-it.sh mysql_container:3306 --timeout=300 --strict -- java -jar app.jar"]

# 1. Base 이미지로 OpenJDK 17 사용
FROM openjdk:21-jdk

# 2. 애플리케이션을 위한 디렉토리 생성 및 설정
WORKDIR /app

# 3. 로컬 jar 파일을 컨테이너의 /app 디렉토리로 복사
COPY build/libs/imagine-0.0.1-SNAPSHOT.jar app.jar

# 4. 포트 노출 (Spring Boot 애플리케이션의 기본 포트)
EXPOSE 8080

# 5. 애플리케이션 실행 명령어
CMD ["java", "-jar", "app.jar"]