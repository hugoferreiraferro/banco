FROM maven:3.9.8-eclipse-temurin-21 AS build
#Encima eu crio uma imagem do maven chamando de build
# dentro do build eu copio todos os arquvios de src e pom
COPY src /app/src
COPY pom.xml /app

WORKDIR /app
#instala o projeto e o - DskipTests fala pro docker pular a fase de testes, como é minha primeira vez deixei assim
#o DskipTests é utilizado para skipar os testes e tratar o problema com a conexa do database
RUN mvn clean install -DskipTests

FROM eclipse-temurin:21-jre-alpine
#copiamos o arquivo da build e mandamos para o app/app.jar
COPY --from=build /app/target/banco-0.0.1-SNAPSHOT.jar /app/app.jar
#trocamos para a /app e passamos a porta mais os comando dos containers
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
