FROM openjdk:8u342-slim
MAINTAINER zaink.cn funyule@vip.qq.com
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom -Xms128m -Xmx512m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"
WORKDIR /app
COPY ./target/*.jar /app/app.jar
ENTRYPOINT ["java","$JAVA_OPTS","-jar","/app.jar"]
