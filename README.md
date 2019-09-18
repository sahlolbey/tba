# 1)Preface 
This a demo project to present my skills in distributed system development using Springboot, Active MQ Messaging server, WebSockets, Design Patterns.

# 2)Project Definition
a. The system has a user interface Web UI that enables the user to instantiate vehicles and tell them to move. 
•	The vehicle movement is commanded by user. 
•	The user send message to vehicle containing direction of movement, distance and speed.
•	Direction of movement is an angle to current movement direction.
•	User can send movement message to vehicles when they completed last movement.
subsequent movement command to a vehicle when it hasn't completed its last movement will be ignored.
•	The vehicle sends its current location to server.
•	We do not need to follow the laws of nature and the vehicles do not need to interact with each other. Simply identifying a collision and then moving over each other in the simulation is not a problem. So for the sake of the case, We can assume the vehicles will stop themselves if they are too close to each other.
Optional:

In this project vehicle is simulated by a system process and there will be an asynchronous messaging  system for communication between different parts of system. 

# 3)Technical Design

As we can understand from system definition it is a simulation, monitoring and controller of robotic vehicles. In this section we will define the system component their responsibilities and how they interact.

![System Block Diagram](link-to-image)
# 3.1) Terminal Web Application
This is a web application responsible for interacting with user and sending user commands to "Vehicle Simulation" component. The communication between "Terminal Web Application" and "Vehicle Simulation" is via asynchronous messaging through ActiveMQ message broker.
This component receives the locations of moving vehicles through asynchronous messaging and sends them to web browser through websocket technology in real time.

# 3.2) Vehicle Simulation
This component is responsible for managing  simulated vehicles. Its interaction with Terminal Web Application is through messaging and it simulates vehicle movement.
When the component receives create command it creates a vehicle and store it in memory. No persistent is implemented in this version as not requested in project definition.
When the component receives move command it finds the requested vehicle and ask it to move. The vehicle create a new thread and simulate the movement  by calculating new locations. The calculated locations is sent to Terminal Web Application through asynchronous messaging each two seconds.



# Project Structure
 
 From coding and implementation perspective the project is divided to three modules.

   - tba-msg directory is contained with the code of "Vehicle Simulation" component of the system as defined in technical 
    design document at  [docs/technical-design.docx](docs/technical-design.docx)
    
   - tba-web directory is contained with the code of "Terminal Web Application" component of the system as defined in technical 
     design document at  [docs/technical-design.docx](docs/technical-design.docx)
   - tba-common directory is contained withe code the both tba-msg module and tba-web is dependent on.
   - docs directory is documents directory for the project.    

# TBA Case Installation Guide
In this document we assume that you already downloaded and  installed
the lasted JDK from Oracle web site. The JDK version should be at least 1.8.

Then you should do the following steps to get the application running.

1. ActiveMQ Installation & Configuration
2. Maven Installation
3. Building application
4. Running tba-msg module
5. Running tba-web module
6. Browse web Console

#1. ActiveMQ Installation & Configuration
    a.Download ActiveMQ from https://activemq.apache.org/components/classic/download/
    b.Extract the archive file to a directory of your choice.
    c. Open a command prompt or terminal and change directory to
        apache-activemq-5.15.9\bin
    d.Run activemq create tbabroker
        it creates a fresh broker to be used by application
    e.Run activemq start
    
#2. Maven Installation
Download Maven from https://maven.apache.org/download.cgi and extract the archive to your 
directory of choice.

put apache-activemq-5.15.9\bin in system path.

#3.Building Application 
   Open a command prompt or terminal and change directory 
   to project directory. Run the following command
    
    mvn package   
         
   after running aboove command you will see some output on console indicating that
   the build is in progress and at the end you should see build successful message.
        
#4. Running tba-msg module
   This is the vehicle simulation component of the system and you should run the following command.Note that you should have ActiveMQ running before.
    
    cd <PROJECT_DIRECTORY> 
    java -jar tba-msg/target/tba-msg-0.0.1-SNAPSHOT.jar
         
#5. Running tba-web module     
   This is the terminal web application component of the system and you should run the follow command.Note that you should have ActiveMQ running before.
   
    cd <PROJECT_DIRECTORY>
    java -jar tba-web/target/tba-web-0.0.1-SNAPSHOT.jar

#6. Browse the web console
   Open the following link in your browser of choice. The system have been tested with firefox but supposed 
   to work in other HTML 5.0 compatible Browsers.
    
    http://localhost:8080/console.html
    
   ![alt text](docs/console.png)
    
   Consult Users Guide document for learning how to use the system.You can find the users guide at
   <PROJECT_DIRECTOR>/docs/user-guide.docx    
