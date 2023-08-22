# Games24x7
To run the framework
platformType=mobile platform=android ./gradlew clean test 

It reads testng.xml file, which has 

Install docker : https://docs.docker.com/desktop/install/mac-install/
Docker hub Url : https://hub.docker.com/settings/security
Run docker login -u anilpatidar36

Start docker : 
docker run -p 8081:8080 -p 50000:50000 --restart=on-failure jenkins/jenkins:lts-jdk11

Access Jenkin 
http://localhost:8081/

Important docker documentation :
Important links:
https://kodekloud.com/blog/docker-create-image-from-container/#
https://github.com/jenkinsci/docker/blob/master/README.md
https://docs.docker.com/engine/reference/commandline/tag/

Copy gradle in the image
cd /Users/anil-patidar/Downloads/
docker cp gradle-8.3 df0f966d7815:/var/gradle

go to the docker (df0f966d7815 is CONTAINER_ID) 
docker ps
docker exec -u root -it df0f966d7815 bash
