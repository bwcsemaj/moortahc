# MOORTAHC
Chatroom for every website...

MOORTAHC is my case study for Per Scholas Intermediate Java Boot Camp. Goal of the project is to allow people to communicate with others on any website they please. 

Ideally users will use my service by copying the URL they wish had a chat feature and paste it into my website. My service would then create a new room with the name passed in (URL) as the room name. If the room name already exist than the user will simply join that room with that name.

A good example of this is say you are watching a UFC fight. You wish the site had a chat feature where you could talk to others watching the same fight. You would copy the URL, go to my site, paste it in, and my service will put you in a room with that name where you can chat with anyone in that room.

This project is still in the works, not all URLs work at this time.

## Technologies:

**Architechture:** Microservices

**Languages:** Java, JavaScript, TypeScript, CSS, HTML
Frameworks/Libraries: SpringBoot, Project Lombok, Spring JPA Data, Spring Cloud Gateway (used mainly for WebSocket/SSE ability), Netflix Zuul (gateway majority API uses), Spring Security, Netflix Eureka, Spring Web Services, Spring WebSocket, Spring WebFlux (used because of SCG)

**Testing:** Mockito, JUnit

**Database(s):** H2

This isn't final list of technologies and some technologies down the line may get replaced (such as Netflix Zuul/Spring Security)...

## Prerequisites to Installation
[Git](https://git-scm.com/downloads) needs to be installed (preferable GitBash also will be downloaded)\
[JDK 14](https://www.azul.com/downloads/zulu-community/?architecture=x86-64-bit&package=jdk) needs to be installed (in parent pom you can change JDK version to 11 and be fine)\
[Intellij](https://www.jetbrains.com/idea/download/#section=windows) needs to be installed\
[Angular CLI](https://angular.io/cli) needs to be installed `npm install -g @angular/cli`

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
3. Change Directory to clone project folder
    1. `cd moortahc`