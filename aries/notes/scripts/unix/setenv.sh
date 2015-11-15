#!/bin/sh

# Local workspace location
export WORKSPACE=/opt/workspace/AEROSPACE

# Maven repository location
export REPOSITORY=/opt/workspace/.m2

# Aerospace development location
export AERO_HOME=$WORKSPACE/aero

# Java development kit location
export JAVA_HOME=/usr/java/jdk1.6.0_20

# Tomcat home location
export TOMCAT_HOME=/opt/apache-tomcat-6.0.26

# Tomcat home location
export MAVEN_HOME=/opt/apache-maven-2.2.1

# adjust command path
export PATH=$AERO_HOME/scripts:$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
