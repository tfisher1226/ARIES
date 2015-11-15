#!/bin/sh

WORKSPACE=c:/workspace/ARIES
PROJECT=infosgi-ear

CWD=`pwd`
cd $WORKSPACE/$PROJECT
mvn clean install -DtargetHost=thebes.kattare.com
cd $CWD


