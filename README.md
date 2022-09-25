## Challenge scenario

We have a need to create a backend API to store and retrieve the data about the cars of our fleet. A "Car" model would
have the following
properties:

- Id
- Brand
- LicensePlate
- Manufacturer
- OperationCity
- Status `enum` (values: `available`, `in-maintenance`, `out-of-service`)
- CreatedAt
- LastUpdatedAt
  A sample JSON representation of such a model would look like (some columns are intentionally omitted):

```json
{
  "id": 12345,
  "brand": "Flexa",
  "licensePlate": "L-CS8877E",
  "status": "available",
  "createdAt": "2017-09-01T10:23:47.000Z",
  "lastUpdatedAt": "2022-04-15T13:23:11.000Z"
}
```

Your challenge is to create a REST API and implement CREATE and READ operations for the "Car" model.

---

## HOW TO:
(assuming you have java and maven installed)
1. run the tests: `./mvnw clean verify`
2. create an executable jar: `./mvnw clean package`
3. run the application: `./mvnw spring-boot:run`
4. test the application manually: after starting the application (3rd point), just open this [web page](http://localhost:8080/swagger-ui/index.html)
