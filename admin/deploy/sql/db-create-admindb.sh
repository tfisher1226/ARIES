#!/bin/sh

. ./setenv-adminDB.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-adminDB.sql
