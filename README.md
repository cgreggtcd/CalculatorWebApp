# CalculatorWebApp

This is a group project completed for CSU33012 Software Engineering.

Run this webapp (pre-release) by cloning the repository and opening the terminal in the directory of the repository. Then, run 

    ./mvnw clean install spring-boot:run

You can then open the current version on localhost:8080/calculator in your browser. 

If you want to run it in Docker, ensure you have run the above already, then run:   

    docker build -t calculator-web-app-image .  

Then, to run the app:

    docker run --name=calculator-web-app-container --rm -d -p 8080:8080 calculator-image 

If you want to run the latest release from DockerHub (currently release 1), run:

    docker run --name=calculator-web-app-container --rm -d -p 8080:8080 clairegregg/calculator-web-app:basic-cal

Then, you can open that on localhost:8080/calculator.