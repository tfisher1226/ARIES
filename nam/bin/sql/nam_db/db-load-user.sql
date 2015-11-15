-- use nam_db;

insert into `email_address` (id, enabled, url, first_name, last_name) values (1, true, 'manager', 'Manager', 'Manager');
insert into `email_address` (id, enabled, url, first_name, last_name) values (2, true, 'tfisher@kattare.com', 'Tom','Fisher');

insert into `person_name` (id, first_name, last_name, middle_initial) values (1, 'Manager', 'Manager', '');
insert into `person_name` (id, first_name, last_name, middle_initial) values (2, 'Tom', 'Fisher', '');

insert into `user` (id, user_name, password_hash, person_name_id, email_address_id, enabled) values (1, 'manager', 'm', 1, 1, true);
insert into `user` (id, user_name, password_hash, person_name_id, email_address_id, enabled) values (2, 'tfisher', '!Hello2u', 2, 2, true);

-- insert into `user` (id, user_name, password_hash, first_name, last_name, email_address_id, enabled) values (1, 'manager', 'm', 'Manager', 'Manager', 1, true);
-- insert into `user` (id, user_name, password_hash, first_name, last_name, email_address_id, enabled) values (2, 'tfisher', '!Hello2u', 'Tom', 'Fisher', 2, true);

INSERT INTO `role` (id, name, role_type, conditional, enabled) VALUES (1, 'manager', 'MANAGER', false, true);
INSERT INTO `role` (id, name, role_type, conditional, enabled) VALUES (2, 'user', 'USER', true, true);

INSERT INTO `user_role` (user_id, role_id) VALUES (1, 1);
INSERT INTO `user_role` (user_id, role_id) VALUES (2, 1);
INSERT INTO `user_role` (user_id, role_id) VALUES (2, 2);

INSERT INTO `permission` (id, enabled, target, organization) VALUES (3, true, null, null);
INSERT INTO `permission` (id, enabled, target, organization) VALUES (4, true, 't1', 'o1');
INSERT INTO `permission` (id, enabled, target, organization) VALUES (5, true, 't2', 'o2');
INSERT INTO `permission` (id, enabled, target, organization) VALUES (6, true, 't3', 'o3');

INSERT INTO `role_permission` (role_id, permission_id) VALUES (1, 3);
INSERT INTO `role_permission` (role_id, permission_id) VALUES (2, 4);
INSERT INTO `role_permission` (role_id, permission_id) VALUES (2, 5);
INSERT INTO `role_permission` (role_id, permission_id) VALUES (2, 6);

INSERT INTO `permission_action` (permission_id, actions) VALUES (3, 'ALL');
INSERT INTO `permission_action` (permission_id, actions) VALUES (4, 'ALL');
INSERT INTO `permission_action` (permission_id, actions) VALUES (5, 'ALL');
INSERT INTO `permission_action` (permission_id, actions) VALUES (6, 'ALL');
