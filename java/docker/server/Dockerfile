FROM maven:3.8.6-openjdk-8

WORKDIR /app/helloworld

COPY ./helloworld /app/helloworld

RUN mvn -DskipTests clean package -Dexec.mainClass=org.example.grpc.App

CMD ["mvn", "-DskipTests", "package", "exec:java", "-Dexec.mainClass=org.example.grpc.server.HelloWorldServer"]

