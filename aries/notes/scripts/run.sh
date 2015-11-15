#!/bin/sh

mvn exec:java -Dexec.mainClass="esb.common.manager.EventManagerMain" -Dexec.args="list"
