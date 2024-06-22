# Installation Guide

## Installing Java 11

### Mac
- https://medium.com/@kirebyte/using-homebrew-to-install-java-jdk11-on-macos-2021-4a90aa276f1c
- https://medium.com/@bectorhimanshu/how-to-set-java-home-environment-variable-on-macos-x-10-9-or-later-versions-dd4fa6936899

### Windows
- https://medium.com/@gauravshah97/how-to-install-java-on-windows-20fb8c329b74


## Installing Docker

### Mac
1. Download Docker Desktop for Mac from the [Docker website](https://docs.docker.com/desktop/install/mac-install/)
2. Install Docker Desktop
3. Run the following command to check if Docker is installed
   ```docker --version```
4. Run the following command to check if Docker Compose is installed
   ```docker-compose --version```
5. You should see the Docker version installed on your system

### Windows
1. Download Docker Desktop for Windows from the [Docker website](https://docs.docker.com/desktop/install/windows-install/)
2. Install Docker Desktop
3. Run the following command to check if Docker is installed
   ```docker --version```
4. You should see the Docker version installed on your system
5. Run the following command to check if Docker Compose is installed
   ```docker-compose --version```

## Installing Database Client

### DBeaver
    - Download the DBeaver from the [DBeaver website](https://dbeaver.io/download/)
    - Install the DBeaver
    - Run the DBeaver
    - Click on the `Database` -> `New Database Connection`
    - Select the `PostgreSQL` from the list
    - Enter the following details:
        - Host: `localhost`
        - Port: `5432`
        - Database: `json_transformation_service`
        - Username: `postgres`
        - Password: `admin`
        - Click on the `Test Connection` to check the connection
        - Click on the `Finish` to save the connection
        - You can now access the database using the DBeaver

### Postico
    - Download the Postico from the [Postico website](https://eggerapps.at/postico/)
    - Install the Postico
    - Run the Postico
    - Click on the `New Favorite` to create a new connection
    - Enter the following details:
        - Host: `localhost`
        - Port: `5432`
        - Database: `json_transformation_service`
        - Username: `postgres`
        - Password: `admin`
        - Click on the `Connect` to check the connection
        - You can now access the database using the Postico
        - You can also save the connection for future use

You can also use any other database client to connect to the database.

## Installing IntelliJ IDEA

1. Visit the [JetBrains website](https://www.jetbrains.com/idea/download/other.html) 
2. Download the IntelliJ IDEA Community Edition based on your operating system
3. Install IntelliJ IDEA
4. Open IntelliJ IDEA
5. Import the project by selecting the project directory