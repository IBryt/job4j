
create table role (
	id serial primary key,
	name varchar(2000)
);

create table users (
	id serial primary key,
	name varchar(2000),
	role_id serial references role(id)
);

create table rules (
	id serial primary key,
	name varchar(2000)
);

create table role_rules (
	id serial primary key,
	role_id serial references  role(id),
	rules_id serial references  rules(id)
);

create table category (
	id serial primary key,
	name varchar(2000)
);

create table state (
	id serial primary key,
	name varchar(2000)
);

create table item (
	id serial primary key,
	name varchar(2000),
	users_id serial unique references users(id),
	category_id serial references category(id),
	state_id serial references state(id)
);

create table comments (
	id serial primary key,
	description varchar(2000),
	item_id serial references item(id)
);

create table attaches (
	id serial primary key,
	name varchar(2000),
	item_id serial references item(id)
);