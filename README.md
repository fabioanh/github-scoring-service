# github-scoring-service
Service to score GitHub repositories

The service exposes a REST API to score GitHub repositories based on the number of stars, forks, and date of the last update.

To rank the repositories, a query parameter is required to filter the repositories by a specific topic. This
parameter is forwarded to the GitHub API to fetch the repositories. The service then scores the repositories based on
the stars, forks and last update date.

The maximum of repositories retrieved is 100 given the limitations of the GitHub API.

## How to run it locally
The application is built using `gradle`. The current files include a gradle wrapper which can be used to build the application.
To do this, execute the following command:

```bash
./gradlew build
```

Once the build is successful, you can run the application using the spring boot plugin:

```bash
./gradlew bootRun
```

The application will start on port `8080` by default.

The rest API documentation is available at `http://localhost:8080/swagger-ui/index.html`.