#!/bin/sh

serverPort=1099

serverName=ec2-184-73-28-177.compute-1.amazonaws.com

$JBOSS_HOME/bin/shutdown.sh -S -s $serverName:$serverPort



