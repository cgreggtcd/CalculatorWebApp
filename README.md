# CalculatorWebApp

This is a group project completed for CSU33012 Software Engineering.

## Running Releases

### Latest Release: Advanced Calculator

To run the latest release, run the following command with Docker Desktop running.

    docker run --name=calculator-web-app-container --rm -d -p 8080:8080 clairegregg/calculator-web-app:advanced-cal
    
Then, the app can be used by opening localhost:8080/calculator in your browser.

### Release 1: Basic Calculator

To run release 1, run the following command with Docker Desktop running.

    docker run --name=calculator-web-app-container --rm -d -p 8080:8080 clairegregg/calculator-web-app:basic-cal
    
Then, the app can be used by opening localhost:8080/calculator in your browser.  

## Running Web App from GitHub

### Containerised

Run this webapp by cloning the repository and opening the terminal in the directory of the repository. Then, run 

    ./mvnw clean install spring-boot:run
    
After that, build the container with:   

    docker build -t calculator-web-app-image .  

Then, to run the app:

    docker run --name=calculator-web-app-container --rm -d -p 8080:8080 calculator-image 

Then, the app can be used by opening localhost:8080/calculator in your browser.  
    
### Not Containerised

Run this webapp by cloning the repository and opening the terminal in the directory of the repository. Then, run 

    ./mvnw clean install spring-boot:run

Then, the app can be used by opening localhost:8080/calculator in your browser.  
