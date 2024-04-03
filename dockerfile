FROM openjdk:17

COPY target/dgloan.jar /app/

WORKDIR /app

EXPOSE 8088

ENTRYPOINT ["java","-jar","dgloan.jar"]
