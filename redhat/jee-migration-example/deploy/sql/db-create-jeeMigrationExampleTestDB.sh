#!/bin/sh

. ./setenv-jeeMigrationExampleTestDB.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-jeeMigrationExampleTestDB.sql
