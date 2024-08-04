# Ericson: XML Parser Application

This application is a Spring Boot service that parses XML files and stores the data in MongoDB. It's containerized using Docker and can be run either as a standalone container or using Docker Compose.

## How It Works

1. The application exposes an API endpoint for uploading XML files.
2. When an XML file is uploaded, the application parses it and stores the data in MongoDB.
3. The parsed data can then be queried or manipulated through other API endpoints .

## Running the Application

### Using Docker Compose (Recommended)

1. Ensure you have Docker and Docker Compose installed on your system.

2. Navigate to the project directory containing the `docker-compose.yml` file.

3. Build and start the services in detached mode: `docker-compose up -d --build`
4. To stop the services: `docker-compose down`
5. To stop the services and remove volumes: `docker-compose down -v`

### Using Docker (Standalone Container)
1. Build the Docker image: `docker build -t ericson .`
2. Run the container: `docker run -d -p 8080:8080 --name ericson ericson`

__Make Sure__ to pass the database connection string to the container as an environment variable. 


## Uploading an XML File

To upload an XML file to the application, use the following curl command:
`curl -X POST -F file="@/path/to/your/file.xml" http://localhost:8080/api/configurations/upload`

__Don't__ remove the `@` symbol from the file name

This command sends a POST request to the `/api/configurations/upload` endpoint with the XML file as form-data.

## Viewing Logs

To view the logs of the running services (when using Docker Compose): `docker-compose logs`

To follow the logs in real-time: `docker-compose logs -f`


## Example Request 
`curl -X POST -F file="@/home/ahmed-hodhod/testat/test.xml" http://localhost:8080/api/configurations/upload`

![Request](screenshots/request.png)

## View the data stored in mongodb  
1. Exec into the mongodb container `sudo docker exec -it xml_parser-mongodb-1  /bin/bash`
2. `mongosh mongodb://admin:password@mongodb:27017/ericson?authSource=admin`
3. List the collections `show collections`

![collections](screenshots/collections.png)

4. View the stored data in a specific collection `db.configurations.find()`

![stored data in mongodb](screenshots/data.png)


