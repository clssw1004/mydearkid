FROM openjdk:17-jdk-slim
#FROM openjdk:17-jre-alpine
# RUN  sed -i 's/deb.debian.org/mirrors.ustc.edu.cn/g' /etc/apt/sources.list \
# &&  sed -i 's|security.debian.org/debian-security|mirrors.ustc.edu.cn/debian-security|g' /etc/apt/sources.list \
# &&  apt update
# RUN apt install libheif-examples -y
# RUN echo "Asia/Shanghai" > /etc/timezone
ARG JAR_FILE=target/mydearkid-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /app.jar
COPY src/main/resources/application.yml /application.yml
ENV JAVA_OPTS="$JAVA_OPTS -Duser.timezone=GMT+08"
ENTRYPOINT ["java","-jar","-Dspring.config.location=/application.yml","/app.jar"]