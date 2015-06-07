#!/usr/bin/env sh
java -cp 'deps/*:build' org.junit.runner.JUnitCore "trail.$1"
