#!/bin/sh

. ./setenv-bookshop2db.sh

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create-bookshop2db.sql
