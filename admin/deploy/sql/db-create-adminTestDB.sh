#!/bin/sh

. ./setenv-adminTestDB.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-adminTestDB.sql
