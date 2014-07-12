#!/bin/bash

mkdir -p /tmp/dockerDemo/
rm -f /tmp/dockerDemo/Pherousa.log
rm -f /tmp/dockerDemo/Tyche.log

if [ -z "`docker images | grep mr_paeddah/docker_demo_hopper`" ]
then
  echo "Building missing atomhopper image"
  docker build --quiet=true -t mr_paeddah/docker_demo_hopper:1.0 atomhopper
  echo "atomhopper images was build"  
fi

ATOMHOPPER=`docker ps -a | grep atom | awk '{print $1}'`
if [ -z "$ATOMHOPPER" ]
then
  echo "Starting a new container for AtomHopper"
  ATOMHOPPER=`docker run -d --name atom -p 18082:8080 mr_paeddah/docker_demo_hopper:1.0`
else
  echo "Restaring an existing container of AtomHopper - Container ID: $ATOMHOPPER"
  docker start $ATOMHOPPER
fi

echo
MONGODB=`docker ps -a | grep mongo | awk '{print $1}'`
if [ -z "$MONGODB" ]
then
  echo "Starting a new container for MongoDb"
  MONGODB=`docker run -d --name mongo -p 27017:27017 dockerfile/mongodb`
else
  echo "Restarting an existing container of MongoDb - Container ID: $MONGODB"
  docker start $MONGODB
fi
echo 

if [ -z "`docker images | grep mr_paeddah/docker_demo_tyche`" ]
then
  echo "Building missing tyche image"
  Tyche/gradlew -b Tyche/build.gradle -q clean boRe
  docker build -q -t mr_paeddah/docker_demo_tyche:1.0 Tyche
  echo "tyche image was build"
  echo 
fi

if [ -z "`docker images | grep mr_paeddah/docker_demo_pherousa`" ]
then
  echo "Building missing pherousa image"
  pherousa/gradlew -b pherousa/build.gradle -q clean boRe
  docker build -q -t mr_paeddah/docker_demo_pherousa:1.0 pherousa
  echo "pherousa image was build"
  echo 
fi


echo "Starting a new container for Tyche"
TYCHE=`docker run -d --link mongo:mongodb -v /tmp/dockerDemo:/var/log -p 18080:8080 mr_paeddah/docker_demo_tyche:1.0`

echo 
echo "Starting a new container for Pherousa"
PHEROUSA=`docker run -d --link mongo:mongodb --link atom:atom -v /tmp/dockerDemo:/var/log -p 18081:18081 mr_paeddah/docker_demo_pherousa:1.0`

echo

echo "Tyche is accessible via http://localhost:18080"
echo "Pherousa is accessible via http://localhost:18081"
echo "AtomHopper is accessible via http://localhost:18082/atomhopper/bookorder/feed"
echo "MongoDb is accessible via localhost:27017"

echo "Logfiles of Tyche and Pherousa are available at /tmp/dockerDemo"

echo "Starting Typhon .. please take into /tmp/Typhon.log to see what happens"
Typhon/gradlew -b Typhon/build.gradle -q clean boRu

echo "Shutdown"
echo "Stopping Pherousa's container"
docker stop $PHEROUSA
echo "Stopping Tyche's container"
docker stop $TYCHE
echo "Stopping MongoDb's container"
docker stop $MONGODB
echo "Stopping AtomHopper's container"
docker stop $ATOMHOPPER
