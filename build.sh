#!/bin/sh
cd $TRAVIS_BUILD_DIR/secret-santa
mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar -Dsonar.login=${SONAR_TOKEN}
