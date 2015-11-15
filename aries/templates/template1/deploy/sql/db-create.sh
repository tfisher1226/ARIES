#!/bin/sh

. ./setenv-template1DB.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-template1DB.sql
