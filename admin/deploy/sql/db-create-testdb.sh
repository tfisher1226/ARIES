#!/bin/sh

. ./setenv-testdb.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-testdb.sql
