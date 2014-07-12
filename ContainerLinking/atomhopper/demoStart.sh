#!/bin/bash

mkdir -p /tmp/dockerDemo/
rm -f /tmp/dockerDemo/Pherousa.log
rm -f /tmp/dockerDemo/Tyche.log

if [ -z "`docker images | grep mr_paeddah/docker_demo_hopper`" ]
then
  echo "building missing atomhopper image"
  docker build --quiet=true -t mr_paeddah/docker_demo_hopper:1.0 atomhopper
  echo "atomhopper images was build"  
fi

ATOMHOPPER=`docker ps -a | grep atom | awk '{print $1}'`
if [ -z "$ATOMHOPPER" ]
then
  echo "Starting new instance of AtomHopper"
  ATOMHOPPER=`docker run -d --name atom -p 8082:8080 mr_paeddah/docker_demo_hopper:1.0`
else
  echo "Restaring existing instance of AtomHopper - Container ID: $ATOMHOPPER"
  docker start $ATOMHOPPER
fi

echo
MONGODB=`docker ps -a | grep mongo | awk '{print $1}'`
if [ -z "$MONGODB" ]
then
  echo "Starting new instance of MongoDb"
  MONGODB=`docker run -d --name mongo -p 27017:27017 dockerfile/mongodb`
else
  echo "Restarting existing instance of MongoDb - Container ID: $MONGODB"
  docker start $MONGODB
fi
echo 

if [ -z "`docker images | grep mr_paeddah/docker_demo_tyche`" ]
then
  echo "building missing tyche image"
  Tyche/gradlew -b Tyche/build.gradle -q clean boRe
  docker build -q -t mr_paeddah/docker_demo_tyche:1.0 Tyche
  echo "tyche image was build"
  echo 
fi

if [ -z "`docker images | grep mr_paeddah/docker_demo_pherousa`" ]
then
  echo "building missing pherousa image"
  pherousa/gradlew -b pherousa/build.gradle -q clean boRe
  docker build -q -t mr_paeddah/docker_demo_pherousa:1.0 pherousa
  echo "pherousa image was build"
  echo 
fi


echo "Starting Tyche"
TYCHE=`docker run -d --link mongo:mongodb -v /tmp/dockerDemo:/var/log -p 18080:8080 mr_paeddah/docker_demo_tyche:1.0`

echo 
echo "Starting Pherousa"
PHEROUSA=`docker run -d --link mongo:mongodb --link atom:atom -v /tmp/dockerDemo:/var/log -p 8081:8081 mr_paeddah/docker_demo_pherousa:1.0`

echo
echo "Starting Typhon"
Typhon/gradlew -b Typhon/build.gradle -q clean boRu

echo "Shutdown"
echo "Stopping Pherousa"
docker stop $PHEROUSA
echo "Stopping Tyche"
docker stop $TYCHE
echo "Stopping MongoDb"
docker stop $MONGODB
echo "Stopping AtomHopper"
docker stop $ATOMHOPPER
