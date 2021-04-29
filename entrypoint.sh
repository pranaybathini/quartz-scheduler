#!/bin/sh

java -XX:+UseParallelGC  -jar -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} quartz-scheduler-0.0.1-SNAPSHOT.jar --net="host"


docker tag <ImageId> yourusername/imagename:tag

