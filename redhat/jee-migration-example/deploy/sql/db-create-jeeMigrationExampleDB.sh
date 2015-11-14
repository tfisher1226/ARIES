#!/bin/sh

. ./setenv-jeeMigrationExampleDB.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-jeeMigrationExampleDB.sql
