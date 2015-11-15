#!/bin/sh

. ./setenv-admindb.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-admin_db.sql
