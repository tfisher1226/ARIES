#!/bin/sh

. ./setenv-admindb.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-admindb.sql
