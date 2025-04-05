# 使用官方的Java运行时作为父镜像
FROM openjdk:8-jdk-alpine


# 设置作者信息
LABEL maintainer="newborne@easyz.cn"

# 将当前目录下的所有文件复制到容器的 /app 目录下
COPY ZHFW/ZHFW-BookStore/target/ZHFW-BookStore-1.0-SNAPSHOT.jar /app/ZHFW-BookStore-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-Warehouse/target/ZHFW-Warehouse-1.0-SNAPSHOT.jar /app/ZHFW-Warehouse-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-Payment/target/ZHFW-Payment-1.0-SNAPSHOT.jar /app/ZHFW-Payment-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-HotelReservation/target/ZHFW-HotelReservation-1.0-SNAPSHOT.jar /app/ZHFW-HotelReservation-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-FlightTicket/target/ZHFW-FlightTicket-1.0-SNAPSHOT.jar /app/ZHFW-FlightTicket-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-Weather/target/ZHFW-Weather-1.0-SNAPSHOT.jar /app/ZHFW-Weather-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-ServiceRetrieval/target/ZHFW-ServiceRetrieval-1.0-SNAPSHOT.jar /app/ZHFW-ServiceRetrieval-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-TravelAgency/target/ZHFW-TravelAgency-1.0-SNAPSHOT.jar /app/ZHFW-TravelAgency-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-Eureka/target/ZHFW-Eureka-1.0-SNAPSHOT.jar /app/ZHFW-Eureka-1.0-SNAPSHOT.jar
COPY ZHFW/ZHFW-Gateway/target/ZHFW-Gateway-1.0-SNAPSHOT.jar /app/ZHFW-Gateway-1.0-SNAPSHOT.jar

COPY ZHFW/datasource /app/datasource

# 暴露应用的端口
EXPOSE 8081

# 设置应用的工作目录
WORKDIR /app

# 运行应用
ENTRYPOINT ["java", "-jar", "ZHFW-BookStore-1.0-SNAPSHOT.jar"]
