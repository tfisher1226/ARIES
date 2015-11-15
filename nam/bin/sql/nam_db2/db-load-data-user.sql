use namdb;

#nsert into email_address (id, enabled, email_address, first_name, last_name) values (1000000, true, 'manager', 'Manager', 'Manager');

insert into user (user_id, password, first_name, last_name, email_address_id, enabled) values ('manager', 'manager', 'Manager', 'Manager', 1000000, true);

