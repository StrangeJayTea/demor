# demor
Finds the cameras closest to a reference camera. Based on an interview question I had a while back.

Consists of three services:

| Service            | Description                                              |
|--------------------|----------------------------------------------------------|
| Floor Plan Service | Provides the cameras available on a floor.               |
| Camera Serivce     | Gives details (such as location) for a camera.           |
| Locator Service    | Tells us which cameras are closest to a reference camera.|

## To Run
The Locator Service currently has three Spring Profiles:
- 'rest'   -- to call the Camera Service and Floor Plan Service to get information
- 'db'     -- to run by itself and rely on an internal database for information (using Spring Data)
- 'db-jpa' -- to run by itself, call the database using old-fashioned JPA

### Option 1
 - run each service separately
 - make sure the Locator Service has its Spring Profile set to 'rest'
 - the Locator Service makes REST calls to the other services

### Option 2
 - only run the Locator Service
 - make sure the Locator Service has its Spring Profile set to 'db'
 - the Locator Service uses data cached in the database
 
### REST URLs
Then call http://localhost:8080/locator/{floorId}/cameras?referenceCameraId={cameraId}&count={maxResults}
ex: http://localhost:8083/locator/3/cameras?referenceCameraId=8&count=10 will find the cameras closest
to cameraId=8 on floor 3 and cap the results at 10 items.
 

## Things to add
Use Spring Cloud to add a Eureka Server to register services, then use
FeignClient to access other services instead of URLs
