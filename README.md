# MOORTAHC
Chatroom for every website...

MOORTAHC is my case study for Per Scholas Intermediate Java Boot Camp. Goal of the project is to allow people to communicate with others on any website they please. 

Ideally users will use my service by copying the URL they wish had a chat feature and paste it into my website. My service would then create a new room with the name passed in (URL) as the room name. If the room name already exist than the user will simply join that room with that name.

A good example of this is say you are watching a UFC fight. You wish the site had a chat feature where you could talk to others watching the same fight. You would copy the URL, go to my site, paste it in, and my service will put you in a room with that name where you can chat with anyone in that room.

This project is still in the works, not all URLs work at this time.

## Technologies:

**Architechture:** Microservices

**Languages:** Java, JavaScript, TypeScript, CSS, HTML

**Frameworks/Libraries:** SpringBoot, Project Lombok, Spring JPA Data, Spring Cloud Gateway (used mainly for WebSocket/SSE ability), Netflix Zuul (gateway majority API uses), Spring Security, Netflix Eureka, Spring Web Services, Spring WebSocket, Spring WebFlux (used because of SCG)

**Testing:** Mockito, JUnit

**Database(s):** H2

This isn't final list of technologies. Some technologies down the line may get replaced (such as Netflix Zuul/Spring Security)...

## Prerequisites to Installation
[Git](https://git-scm.com/downloads) needs to be installed (preferable GitBash also will be downloaded)\
[JDK 14](https://www.azul.com/downloads/zulu-community/?architecture=x86-64-bit&package=jdk) needs to be installed (in parent pom you can change JDK version to 11 and be fine)\
[Intellij](https://www.jetbrains.com/idea/download/#section=windows) needs to be installed (recommended having Intellij Ultimate)\
[Angular CLI](https://angular.io/cli) needs to be installed `npm install -g @angular/cli`\
[Chrome](https://www.google.com/chrome/) needs to be installed (unless you know how to disable web security on other browsers)

## Brief Installation Steps
##### This section is "quick" installation guide
1. clone project `git clone https://github.com/bwcsemaj/moortahc`
2. load project multi-module maven project in Intellij (make sure you open in parent directory and not a specific module of the project)
3. build each module (maven package)
4. run each module's Driver class (besides common)
5. start Angular by navigating to ../client/src/main/frontend and entering in the command `ng serve`
6. start chrome with security disabled by navigating to chrome.exe folder and typing in `chrome.exe --disable-web-security --disable-gpu --user-data-dir=C:\Chrome dev session2` (user data directory can be whatever you want it to be but has to be in a folder you have access to)
7. go to http://localhost:4200 inside the chrome browser that has web security disabled

## Indepth Windows Installation Steps
1. transverse to a directory you wish to have copy of project located
    1. Press Windows Key
    2. Type GitBash
    3. Hit Enter
    4. Go to location you want project stored
        1. option1: change directory to directory you want to have project in `cd pathToDirectory`
        2. option2: make a new directory you want the project stored `mkdir newDirectoryName` then cd into new folder `cd newDirectoryName`
2. Clone project into direcotry
    1. `git clone https://github.com/bwcsemaj/moortahc`
    2. you can add an argument at tend to specifically name folder project will be in otherwise will default to moortahc
        1. `git clone https://github.com/bwcsemaj/moortahc newDirectoryName`
3. Change Directory to clone project folder
    1. `cd moortahc`
    2. if you chose specific name cd into that name `cd newDirectoryName`
4. Open up explorer inside project path
    1. type `explorer .`
    2. the explorer program should open up a window in that specific path
5. You should see all modules, pom, README.md... right click pom.xml
6. chose `edit with Intellij IDEA`
    1. Intellij should begin to load the project as a multi module maven project
    2. Intellij should also recognize all the Spring Boot configs (not 100% needed)
7. All projects should be blue, pom.xml maven icon should be blue
8. right click the very top directory 
    1. should say the folder name `moortahc [parent]`, parent shouldn't be folder name but indicate it is the parent of project
9. hover over `Run Maven`
10. choose `package`
    1. maven will begin to create a package/jar for each of the modules
    2. this isn't 100% needed but it will download the dependencies, we could also do `maven compile`
11. (OPTIONAL: NEED Intellij Ultimate) once the build finishes...
    1. Look at top of Intellij window `File Edit View Navigate...`
    2. Press `View`
    3. Press `Tool Windows`
    4. Select `Services`
    5. This will add a tab at the bottom of the screen to view all our micro services nicely
    6. Service tab should recognize 7 Driver classes, one for each of our microservices
        1. AuthenticateDriver : Port 8082 needs to be open
        2. CommentDriver : Port 8083 needs to be open
        3. DiscoveryDriver : Port 8761 needs to be open
        4. GatewayDriver : Port 8086 needs to be open
        5. PostDriver : Port 8084 needs to be open
        6. SpringCloudGatewayDriver : Port 8080 needs to be open
        7. RoomDriver : Port 8085 needs to be open
    7. If a driver is missing you will have to navigate to module and start Driver manually
    8. Highlight all Drivers 
    9. Right click them and hit `Run` or `Rerun`
    10. All the services should be running on their own port (ports are hard coded atm)
12. If you succeed with step 11 than step 12, Run all Drivers manually
    1. Start Discovery by running application in DiscoveryDriver
        1. Inside Intellij click on server.discovery
        2. click on src
        3. click on main
        4. click on java
        5. click on com.moortahc.server.discovery
        6. double click on DiscoveryDriver (DiscoveryDriver java file should be opened)
        7. left of psvm (public static void main) line, click the green arrow
        8. click on `Run DiscoveryDriver`
    2. repeat steps 12.1 - 12.8 with the rest of the services
        1. Each driver should be located at src/main/java/com.moortahc.server.{insert-name}
        2. Each driver should have a green arrow left of psvm that you can Run the application
13. After all micro services are running, we need to start frontend Angular Server
    1. switch to your Git Bash
    2. you should be still located in start of project path
    3. navigate to frontend folder in client `cd client/src/main/frontend`
    4. Now this is why we need Angular CLI, to serve frontend in dev mode we need to use Angular CLI
        1. enter in `ng serve` into Git Bash
        2. it may look like it isn't doing anything but please wait, the project has to be compiled and served up
        3. You should get a `Angular Live Development Server is listening...` in Git Bash. The Server will be listening in on port 4200
        4. url to get to the website is localhost:4200 but before we can do that we need to have web security disabled in our browsers
            1. reason is CORS is enabled on server side atm. I need to do a bit more research 
            on subject in order to determine if it is a good idea, from my understanding if all the servers are located at the same origin I won't need to disable CORS
14. View the site inside a browser with web security/CORS disabled
    1. We are going to use Chrome
    2. open up another Git Bash window or CMD (you might want to load it up in admin mode)
    3. Navigate to chrome.exe location on your computer
        1. mine Chrome installation is located on C: drive, so `cd C:\Program Files (x86)\Google\Chrome\Application`
        2. now load chrome with web security disabled, `chrome.exe --disable-web-security --disable-gpu --user-data-dir=C:\Chrome dev session`
        3. the user data directory can be whatever you please but you need to folder/file creation permission
    4. You will know if you did this right if you get `You are using an unsupported command-lin flag:--disable-web-security. Stability and security will suffer` underneath the URL
15. Inside Chrome type `localhost:4200` in the URL and press enter

            