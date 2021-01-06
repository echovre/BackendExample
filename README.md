# SpringRestExample
To Build:
##git clone <repo>
##maven clean install
##mvn -N io.takari:maven:wrapper

To Run/Test:
##./mvnw spring-boot:run
##http://localhost:8080/greeting?name=blah
##curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"body":"value"}' http://localhost:8080/request
