# 使用官方的Java运行时作为父镜像
FROM openjdk:8-jdk-alpine


# 设置作者信息
LABEL maintainer="newborne@easyz.cn"

# 将当前目录下的所有文件复制到容器的 /app 目录下
COPY target/ZHFW-FlightTicket-1.0-SNAPSHOT.jar /app/ZHFW-FlightTicket-1.0-SNAPSHOT.jar
COPY /datasource /app/datasource

# 暴露应用的端口
EXPOSE 8086

# 设置应用的工作目录
WORKDIR /app

# 运行应用
ENTRYPOINT ["java", "-jar", "ZHFW-FlightTicket-1.0-SNAPSHOT.jar"]
