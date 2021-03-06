drop database if exists sampleDB;
create database sampleDB;
use mysql;
delete from user where user='sample';
delete from db where user='sample';
FLUSH PRIVILEGES;
create user 'sample'@'localhost' identified by 'sample';
create user 'sample'@'%' identified by 'sample';
set password for sample = password('sample');
grant all on sampleDB.* to sample;
grant all on sampleDB.* to sample@localhost identified by 'sample';
use sampleDB;
