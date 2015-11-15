use mysql;
grant all on namDB.* to 'manager';
grant all on namDB.* to 'manager'@'localhost' identified by 'manager';
grant all on namDB.* to 'manager'@'%' identified by 'manager';
