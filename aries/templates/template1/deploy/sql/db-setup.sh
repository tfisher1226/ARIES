#!/bin/sh

. ./setenv-template1DB.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-template1DB.sql

#mysql --user=$USER --password=$PASSWORD --host=$HOST --database=$DATABASE < db-load-schema.sql

#mysql --user=$USER --password=$PASSWORD --host=$HOST --database=$DATABASE < db-load-data.sql

#mysql --user=$USER --password=$PASSWORD --host=$HOST --database=$DATABASE < db-load-data-user.sql

#mysql --user=$USER --password=$PASSWORD --host=$HOST --database=$DATABASE < db-load-test-data.sql
