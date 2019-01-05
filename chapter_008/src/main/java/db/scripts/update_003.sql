create table countries (
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(2000)
);

create table towns (
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(2000),
  countries_id serial references countries(id)
);

insert into countries(id, name) VALUES (1, 'Ukraine');
insert into countries(id, name) VALUES (2, 'Russia');

insert into towns(id, name, countries_id) VALUES (1, 'Kiev', 1);
insert into towns(id, name, countries_id) VALUES (2, 'Moscow', 2);