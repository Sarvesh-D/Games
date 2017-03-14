#!/bin/sh
cd $TRAVIS_BUILD_DIR/secret-santa
mvn clean package
mvn cobertura:cobertura
