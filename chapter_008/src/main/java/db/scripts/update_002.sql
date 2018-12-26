create table role (
  id   SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(2000),
  editAll BOOLEAN
);

ALTER TABLE users ADD COLUMN password varchar(200);
ALTER TABLE users ADD COLUMN role_id serial references role(id);
-- ALTER TABLE public.users DROP CONSTRAINT users_role_id_fkey;

insert into role(id, name, editAll) VALUES (1, 'admin', true);
insert into role(id, name, editAll) VALUES (2, 'user', false);
insert into users(name, login, email, Date, password, role_id) values('root', 'root', 'root@mail.com', now(), 'root', 1);
