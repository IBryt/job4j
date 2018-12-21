create table users (
  id serial primary key not null,
  name varchar(2000),
  login varchar(2000),
  email varchar(2000),
  Date TIMESTAMP
);