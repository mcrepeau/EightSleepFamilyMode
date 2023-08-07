# Eight Sleep Family Mode

This repo contains my implementation of a solution for the assignment given by Eight Sleep. It uses Java 17 and Spring Boot 3 to implement the back-end service underpinning this feature.

## Introduction
For this assignment, Java and Spring Boot were chosen for their reliability, performance, and the ease with which they can be used to implement RESTful APIs.
The thought process for tackling this assignment was that each of the 3 JSON files given as an example would correspond to the sleep data for 3 different users: Alice, Bob, and Charlie (in the real world, those would probably be backed by a database - for example they could be documents in a MongoDB collection). Then the rest of the solution would center around retrieving that data.

The basic requirements given were that:
* The user can see all family members
* The user can select the family member and see their data
* The user can see the selected family member's data

Therefore, the choice was made to implement 2 controllers, one for the querying users by userId and householdId, `UserController` and another that pertains to getting the sleep data for a given user, `SleepDataController`.

Since the UI screenshots given as a reference include "Day" and "Week" views, the API to get the sleep data includes parameters to define a timestamp range.

## Assumptions and trade-offs

Given the nature of the assignment and the timeframe, a few assumptions and trade-offs had to be made, among which:
* The household is abstracted and not defined separately in the data model
* A user can only belong to a single household
* A user can view all the other users in the household (permissions are defined but not enforced)
* No authentication mechanism is implemented at the API level

## High-level design

### Data model
#### SleepData
The SleepData class represents sleep-related data. It is the highest level of the data model and stores an array of intervals.

Properties:
* intervals : A list of intervals containing sleep data.
#### Interval
The Interval class represents a sleep session with its related information.

Properties:
* id : A unique identifier for the interval.
* ts : The timestamp representing the start time of the interval.
* stages : A list of the sleep stages that occurred during the interval.
* score : An integer value representing the sleep score associated with the interval.
* timeseries : A map of time series data associated with the interval. The key is a string representing the name of the time series, and the value is a list of TimeSeriesData objects.

*Note: The timeseries property uses a custom TimeSeriesDeserializer since the data model implemented differs from the one in the JSON input.*

##### Stage
The Stage class represents a specific sleep stage and its duration during a sleep session.

Properties:
* stage : The name of the sleep stage.
* duration : The duration of the sleep stage in seconds.

##### TimeSeriesData
The TimeSeriesData class represents a data point in a timeseries.

Properties:
* timestamp (String): The timestamp of the data point.
* value (double): The value associated with the data point.

##### User
The User class represents a user within the system.

Properties:
* id : A unique identifier for the user.
* householdId : The identifier of the household to which the user belongs.
* name : The name of the user.
* role : The role of the user, which is one of the values from the Role enum.

##### Role
The Role enum represents the role of a user.

Values:
* PRIMARY: Denotes a primary user role.
* LIMITED: Denotes a limited user role.

### API
* GET `/v1/household/{householdId}/users` - This endpoint returns a list of users belonging to an account with the specified `hous`.
* GET `/v1/user/{userId}` - This endpoint returns user information when given a specific `userId`.
* GET `/v1/sleepdata/{userId}` - This endpoint returns the sleep data for a user when given a specific `userId` with an option to specify the time range for that data.

## Running the service
### Requirements
This code requires JDK version 17+ and Maven version 3.8.1+ to be installed on the local machine. The `JAVA_HOME` and `MAVEN_HOME` environment variables must be properly
set to ensure that it all works smoothly.
### How to run locally
Run ``# mvn spring-boot:run`` to start the service. It will expose an HTTP endpoint at [http://localhost:8080](http://localhost:8080)

### CLI
cURL can be used to make requests via the command line, for example: 
```
# curl -X 'GET' 'http://localhost:8080/v1/household/0/users' -H 'accept: */*'
[{"id":1,"householdId":0,"name":"Alice","role":"PRIMARY"},{"id":2,"householdId":0,"name":"Bob","role":"LIMITED"}]
```

### UI
After the service has started, a Swagger UI can be found at the following address: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
It documents the APIs as well as the data model and allows the user/tester to make requests easily.

