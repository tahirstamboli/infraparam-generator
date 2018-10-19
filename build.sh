#!/bin/sh

home=$(pwd) &&
mvn clean &&
mvn clean compile assembly:single &&
mv target/infraparam-generator-0.0.1-jar-with-dependencies.jar $home/infraparam-generator-0.0.1.jar
