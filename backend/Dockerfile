FROM openjdk:17

WORKDIR /app

COPY . .

RUN chmod +x mvnw || true
RUN ./mvnw clean package || mvn clean package

CMD ["sh", "-c", "java -jar target/*.jar"]
