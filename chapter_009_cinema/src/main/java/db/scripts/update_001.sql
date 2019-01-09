create table hall (
  id serial primary key not null,
  name varchar(200),
  row INT,
  place INT
);

create table accounts (
  id serial primary key not null,
  name varchar(200),
  phone varchar(200)
);

create table tickets (
  id serial primary key not null,
  hall_id serial not null references hall(id),
  accounts_id serial not null references accounts(id)
);


insert into hall(id, name, row, place) VALUES (1, 'Ряд 1, Место 1', 1, 1);
insert into hall(id, name, row, place) VALUES (2, 'Ряд 1, Место 2', 1, 2);
insert into hall(id, name, row, place) VALUES (3, 'Ряд 1, Место 3', 1, 3);

insert into hall(id, name, row, place) VALUES (4, 'Ряд 2, Место 1', 2, 1);
insert into hall(id, name, row, place) VALUES (5, 'Ряд 2, Место 2', 2, 2);
insert into hall(id, name, row, place) VALUES (6, 'Ряд 2, Место 3', 2, 3);

insert into hall(id, name, row, place) VALUES (7, 'Ряд 3, Место 1', 3, 1);
insert into hall(id, name, row, place) VALUES (8, 'Ряд 3, Место 2', 3, 2);
insert into hall(id, name, row, place) VALUES (9, 'Ряд 3, Место 3', 3, 3);

insert into accounts(id, name, phone) VALUES (1, 'customer1', '1234567890');

insert into tickets(id, hall_id, accounts_id) VALUES (1, 1, 1);