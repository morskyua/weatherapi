# Getting Started

## Clone the Repository
To get started, clone the repository to your local machine:

```bash
git clone https://github.com/example/weather-application.git
cd weather-application
```

## Configuration

### Update API Key
The application retrieves weather data using the OpenWeatherMap API. You need to generate an API key and configure it in the application.

1. Sign up for a free account at OpenWeatherMap.
2. Generate an API key in the API keys section.
3. Add the API key to the `application.yml` file located in `src/main/resources/`:

```yaml
api:
  weather:
    url: https://api.openweathermap.org/data/2.5/weather
    key: YOUR_API_KEY
```

Replace `YOUR_API_KEY` with the API key from OpenWeatherMap.

## Run the Application

### Build the Application
Run the following command to build the application:

```bash
mvn clean install
```

### Run the Application
You can run the application using:

```bash
mvn spring-boot:run
```

Alternatively, if you have created a runnable JAR:

```bash
java -jar target/weather-application-1.0-SNAPSHOT.jar
```

Once the application starts, it will run on [http://localhost:8080](http://localhost:8080).

## REST API Endpoints

### Get Weather by City Name:

```http
GET /api/weather?cityName={cityName}
```

**Example Request:**

```http
GET http://localhost:8080/api/weather?cityName=London
```

**Example Response:**

```json
{
  "cityName": "London",
  "zipCode": null,
  "temperature": 15.0,
  "weatherDescription": "Clear sky"
}
```

### Get Weather by Zip Code:

```http
GET /api/weather?zipCode={zipCode}
```

**Example Request:**

```http
GET http://localhost:8080/api/weather?zipCode=10001
```

**Example Response:**

```json
{
  "cityName": "New York",
  "zipCode": "10001",
  "temperature": 25.5,
  "weatherDescription": "Cloudy"
}
```

### Error Handling:
- **400 Bad Request**: If both `cityName` and `zipCode` are missing in the request.
- **404 Not Found**: If no weather data is available for the provided city or zip code.

## Scheduled Weather Refresh
The application automatically refreshes weather data periodically using Spring Scheduler.

By default, the data refresh period is **1 hour (3600000 milliseconds).**
This can be configured in the `application.yml` file under the `scheduler.refresh.interval` property:

```yaml
scheduler:
  refresh:
    interval: 3600000 # Refresh every 1 hour
```

## Database Configuration
The application uses an **H2 in-memory database** for local development, which resets every time the application restarts.

### Default Configuration
The default database configuration is in `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:weatherdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Viewing Database
You can access the H2 console through the following URL after starting the application:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

Use the following credentials:

- **JDBC URL**: `jdbc:h2:mem:weatherdb`
- **Username**: `sa`
- **Password**: (leave blank)

## Testing the Application

### Running Unit Tests
The project includes unit tests for key components (e.g., `WeatherService`) with a goal of maintaining **80%+ coverage**. To run the tests, execute:

```bash
mvn test
```

### Code Coverage
The project uses **JaCoCo** for code coverage reporting. To generate a coverage report:

1. Run the tests using the following command:

```bash
mvn clean verify
```

2. Find the code coverage report in `target/site/jacoco/index.html`.

## Quality Checks

### Static Code Analysis
The project uses **Checkstyle** for linting and code style checks. You can run Checkstyle with the following command:

```bash
mvn checkstyle:check
```

### Dependency Analysis
To detect any dependency issues in the project, run:

```bash
mvn dependency:analyze
```

## Useful Project Commands

### Build and Run
```bash
mvn clean spring-boot:run
```

### Run Unit Tests
```bash
mvn test
```

### Static Code Analysis (Checkstyle)
```bash
mvn checkstyle:check
```

### Additional Features:
- Support for more weather APIs, such as WeatherAPI or AccuWeather.
- Historical weather data storage for analysis.
- Frontend integration using React or Angular to display weather data visually.

## Troubleshooting

### Problem: No weather data is returned for a query.
**Solution**: Verify that your OpenWeatherMap API key is correct and that the city or zip code provided is valid.

### Problem: H2 Console is not working.
**Solution**: Check that `spring.h2.console.enabled=true` is set in `application.yml`.

### Problem: Hibernate-related exceptions are raised.
**Solution**: Ensure the `WeatherEntity` class is properly annotated and matches the database schema.


