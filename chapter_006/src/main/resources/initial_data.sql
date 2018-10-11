
insert into role(id, name) values(1, 'full');
insert into role(id, name) values(2, 'admin');

insert into users(id, name, role_id) values(1, 'user1', 1);
insert into users(id, name, role_id) values(2, 'user2', 1);
insert into users(id, name, role_id) values(3, 'user3', 2);

insert into rules(id, name) values(1, 'add');
insert into rules(id, name) values(2, 'delete');
insert into rules(id, name) values(3, 'update');

insert into role_rules(id, role_id, rules_id) values(1, 1, 1);
insert into role_rules(id, role_id, rules_id) values(2, 1, 2);
insert into role_rules(id, role_id, rules_id) values(3, 1, 3);
insert into role_rules(id, role_id, rules_id) values(4, 2, 1);
insert into role_rules(id, role_id, rules_id) values(5, 2, 3);

insert into category(id, name) values(1, 'easy');
insert into category(id, name) values(2, 'difficult');


insert into state(id, name) values(1, 'waiting');
insert into state(id, name) values(2, 'in working');

insert into item(id, name, users_id, category_id, state_id) values(1,'task1', 1, 1, 1);
insert into item(id, name, users_id, category_id, state_id) values(2,'task2', 2, 2, 2);

insert into comments(id, description, item_id) values(1, 'comments1', 1);
insert into comments(id, description, item_id) values(2, 'comments2', 1);
insert into comments(id, description, item_id) values(3, 'comments3', 2);
insert into comments(id, description, item_id) values(4, 'comments4', 2);

insert into attaches(id, name, item_id) values(1, 'attaches1', 1);
insert into attaches(id, name, item_id) values(2, 'attaches2', 1);