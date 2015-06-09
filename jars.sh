#!/usr/bin/env sh
wget -O deps/junit.jar \
    http://search.maven.org/remotecontent?filepath=junit/junit/4.12/junit-4.12.jar

wget -O deps/hamcrest.jar \
    http://search.maven.org/remotecontent?filepath=org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

wget -O deps/jimfs.jar \
    http://search.maven.org/remotecontent?filepath=com/google/jimfs/jimfs/1.0/jimfs-1.0.jar
