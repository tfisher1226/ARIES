use mysql;
drop database if exists admin_db;
create database admin_db;
delete from user where user='admin';
delete from db where user='admin';
FLUSH PRIVILEGES;
create user 'admin'@'localhost' identified by 'password';
create user 'admin'@'%' identified by 'password';
set password for manager = password('password');
grant all on admin_db.* to 'admin';
grant all on admin_db.* to 'admin'@'localhost' identified by 'password';
grant all on admin_db.* to 'admin'@'%' identified by 'password';
use admin_db;
