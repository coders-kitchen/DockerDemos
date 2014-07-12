#!/bin/bash

if [ -z "`docker images | grep unittest/postgresql`" ]
then
  echo "Building missing postgresql image"
  docker build --quiet=true -t unittest/postgresql postgres
  echo "postgresql images was build"  
fi

./gradlew clean test

POSTGRES_CONTAINER=` docker ps -a | grep unit | head -n1 | awk '{print $1}'`
echo "restaring postgres container"
docker start $POSTGRES_CONTAINER
POSTGRES_IP=`docker inspect --format="{{.NetworkSettings.IPAddress}}" $POSTGRES_CONTAINER`
sleep 5
psql -h $POSTGRES_IP -c "SELECT * FROM book" -U docker
echo "stopping postgres container"
docker stop $POSTGRES_CONTAINER
