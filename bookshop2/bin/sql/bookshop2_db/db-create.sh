#!/bin/sh

. ./setenv

mysql --user=$USER --password=$PASSWORD --host=$HOST < db-create.sql
