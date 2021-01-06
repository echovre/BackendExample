#To Build:
```
git clone <repo>

mvn -N io.takari:maven:wrapper

maven clean install

```
You'll also need a MongoDB instance for persistence at localhost:27017
 (unauthenticated, default setup should be fine)


#To Run/Test:
```
./mvnw spring-boot:run

curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d '{"body":"somebodystuff"}' http://localhost:8080/callback/1234
 
curl -H "Accept: application/json" -H "Content-type: application/json" -X POST -d 'STARTED' http://localhost:8080/callback/1234

curl -H "Accept: application/json" -H "Content-type: application/json" -X PUT -d '{"status":"COMPLETED","detail":"blah"}' http://localhost:8080/callback/1234

curl -H "Accept: application/json" -H "Content-type: application/json" -X GET http://localhost:8080/status/1234

```
To see database entries:

``` 
mongo

use test

db.transactions.find()
```